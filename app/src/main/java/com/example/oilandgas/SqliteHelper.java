package com.example.oilandgas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.UUID;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userstore.db";

    public static final String []PRODUCT_TABLE_NAMES= {"id", "name", "price", "image", "category"};

    public static final String []CATEGORY_TABLE_NAMES= {"id", "name"};

    public static final String []CART_TABLE_NAMES= {"id", "product"};
    private static final int SCHEMA = 1;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS \"category\";");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS \"category\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS \"product\";");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS \"product\" (\n" +
                "\t\"id\"\tTEXT NOT NULL,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"price\"\tTEXT,\n" +
                "\t\"image\"\tINTEGER,\n" +
                "\t\"category\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"category\") REFERENCES \"category\"(\"id\"),\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS \"cart\";");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS \"cart\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL,\n" +
                "\t\"product\"\tTEXT UNIQUE,\n" +
                "\tFOREIGN KEY(\"product\") REFERENCES \"product\"(\"id\"),\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");

        System.out.println("aaa");
        sqLiteDatabase.execSQL("INSERT INTO category(name) VALUES(\"OIL\");");
        System.out.println("bbb");
        sqLiteDatabase.execSQL("INSERT INTO category(name) VALUES(\"GAS\");");
        System.out.println("ccc");
        sqLiteDatabase.execSQL(String.format("INSERT INTO product(id, name, price, image, category) \n" +
                "VALUES(\"%s\", \"%s\", \"%s\", %d, %d);",
                UUID.randomUUID().toString(), "Neft quvur", "100", R.drawable.oil, 1));

        sqLiteDatabase.execSQL(String.format("INSERT INTO product(id, name, price, image, category) \n" +
                        "VALUES(\"%s\", \"%s\", \"%s\", %d, %d);",
                UUID.randomUUID().toString(), "Neft quvur 12RE", "200", R.drawable.oil2, 1));

        sqLiteDatabase.execSQL(String.format("INSERT INTO product(id, name, price, image, category) \n" +
                        "VALUES(\"%s\", \"%s\", \"%s\", %d, %d);",
                UUID.randomUUID().toString(), "Fura 154FG", "500", R.drawable.car, 1));

        sqLiteDatabase.execSQL(String.format("INSERT INTO product(id, name, price, image, category) \n" +
                        "VALUES(\"%s\", \"%s\", \"%s\", %d, %d);",
                UUID.randomUUID().toString(), "Ekofluid", "100", R.drawable.gas1, 2));

        sqLiteDatabase.execSQL(String.format("INSERT INTO product(id, name, price, image, category) \n" +
                        "VALUES(\"%s\", \"%s\", \"%s\", %d, %d);",
                UUID.randomUUID().toString(), "Ekofluid132", "300", R.drawable.gas2, 2));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS product;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS category;");
        onCreate(sqLiteDatabase);
    }
}