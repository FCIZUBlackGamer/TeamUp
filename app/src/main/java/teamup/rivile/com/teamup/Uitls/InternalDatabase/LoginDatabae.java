package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class LoginDatabae extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "User";

    private static final String TABLE_NAME = "TABLE_Local";

    private static final String UID = "id";

    private static final String FullName = "name";

    private static final String USER_ID = "userId";

    private static final String PHONE = "phone";

    private static final String TYPE = "password";

    private static final String IMAGE = "image"; //http://www.selltlbaty.sweverteam.com/

    private static final String AccountType = "account_type";

    private static final String Mail = "mail";

    private static final int DATABASE_VERSION = 2;
    Context cont;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " +TABLE_NAME +
            "( " + UID + " integer primary key , "
            + FullName + " varchar(255) not null, "
            + USER_ID + " varchar(255) , "
            + PHONE + " varchar(20) , "
            + TYPE + " varchar(255) not null, "
            + IMAGE + " varchar(255), "
            + AccountType + " varchar(255) , "
            + Mail + " varchar(255) );";

    // Database Deletion
    private static final String DATABASE_DROP = "drop table if exists "+TABLE_NAME+";";

    public LoginDatabae(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
            db.execSQL("insert into " + TABLE_NAME + " ( " + UID + ", " + FullName + ", " + USER_ID + ", " + PHONE + ", " + TYPE + "," + IMAGE + "," + AccountType +  "," + Mail +  ") values ( '1', 'e', '0', '0', '0', '0', '0', '0');");
            // Toast.makeText(cont,"تم إنشاء سله تسوق", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't created " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
            Toast.makeText(cont,"تم تحديث سله التسوق", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't upgraded " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean InsertData (String name, String userid , String phone, String type, String image)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FullName,name);
        contentValues.put(USER_ID,userid);
        contentValues.put(PHONE,phone);
        contentValues.put(TYPE,type);
        contentValues.put(IMAGE,image);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return result==-1?false:true;
    }

    public Cursor ShowData ()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME+" ;",null);
        return cursor;
    }

    public boolean UpdateData (String id, String name, String userid , String phone, String type, String image, String AccountTyp, String Mal)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID,id);
        contentValues.put(FullName,name);
        contentValues.put(USER_ID,userid);
        contentValues.put(PHONE,phone);
        contentValues.put(TYPE,type);
        contentValues.put(IMAGE,image);
        contentValues.put(AccountType,AccountTyp);
        contentValues.put(Mail,Mal);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id = "+ Integer.parseInt( id ),null);

        return true;
    }

    public boolean UpdateData (String id,
                               String AccountTyp)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID,id);
        contentValues.put(AccountType,AccountTyp);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id = "+ Integer.parseInt( id ),null);

        return true;
    }

    public int DeleteData (String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[] {id});
    }
}
