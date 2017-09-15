package com.projeto.patyalves.projeto.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abceducation on 12/09/17.
 */

public class DBHandlerP extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "matchplaces";

    // Table Names
    private static final String TABLE_USER = "users";
    //private static final String TABLE_USER = "usuarios";
    private static final String TABLE_LOCAL = "locals";

    // Common column names
    private static final String KEY_ID = "id";

    // Users Table - column nmaes
    private static final String KEY_USUARIO = "username";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_SECRET = "secret";
    private static final String KEY_IDTWITTER = "idtwitter";
    private static final String KEY_IDPESSOA = "idpessoa";

    // Local Table - column names
    private static final String KEY_IDLOCAL = "idlocal";
    private static final String KEY_NAME = "name";
    private static final String KEY_BAIRRO = "bairro";
    private static final String KEY_IMAGEM = "imagem";
    private static final String KEY_RATE = "rate";
    private static final String KEY_MYRATE = "myRate";
    private static final String KEY_MYCOMENTARIO = "myComentario";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + KEY_USUARIO + " TEXT,"
            + KEY_SENHA + " TEXT,"
            + KEY_TOKEN + " TEXT,"
            + KEY_SECRET + " TEXT,"
            + KEY_IDTWITTER + " TEXT,"
            + KEY_IDPESSOA + " INTEGER" + ")";

    private static final String CREATE_TABLE_LOCAL = "CREATE TABLE " + TABLE_LOCAL + " ("
            + KEY_IDLOCAL + " TEXT,"
            + KEY_NAME + " TEXT,"
            + KEY_BAIRRO + " TEXT,"
            + KEY_IMAGEM + " BLOB,"
            + KEY_RATE + " REAL,"
            + KEY_MYRATE + " REAL,"
            + KEY_MYCOMENTARIO + " TEXT" + ")";



    public DBHandlerP(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LOCAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL);

        Log.i("carregaUser","onUpgrade");

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a user
 */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO, user.getUsuario());
        values.put(KEY_SENHA, user.getSenha());
        values.put(KEY_TOKEN, user.getTokenTwitter());
        values.put(KEY_SECRET, user.getSecretTwitter());
        values.put(KEY_IDTWITTER, user.getUserIdTwitter());
        values.put(KEY_IDPESSOA, user.getIdpessoa());

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);

        return user_id;
    }

    /*
 * get single user
 */
    public User getUser(User userSearch) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USUARIO + " = '" + userSearch.getUsuario()+"'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            User user = new User();
            user.setUsuario((c.getString(c.getColumnIndex(KEY_USUARIO))));
            user.setSenha(c.getString(c.getColumnIndex(KEY_SENHA)));
            user.setTokenTwitter(c.getString(c.getColumnIndex(KEY_TOKEN)));
            user.setSecretTwitter(c.getString(c.getColumnIndex(KEY_SECRET)));
            user.setUserIdTwitter(c.getString(c.getColumnIndex(KEY_IDTWITTER)));
            user.setIdpessoa(c.getLong(c.getColumnIndex(KEY_IDPESSOA)));
            return user;
        }else{
            Log.i("carregaUser","tem Nada aqui para ver.");
            return null;
        }

    }

    /*
* get single user
*/
    public User getLogin(User userSearch) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USUARIO + " = '" + userSearch.getUsuario()+"' AND " + KEY_SENHA  + " = '" + userSearch.getSenha()+"'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            User user = new User();
            user.setUsuario((c.getString(c.getColumnIndex(KEY_USUARIO))));
            user.setSenha(c.getString(c.getColumnIndex(KEY_SENHA)));
            user.setTokenTwitter(c.getString(c.getColumnIndex(KEY_TOKEN)));
            user.setSecretTwitter(c.getString(c.getColumnIndex(KEY_SECRET)));
            user.setUserIdTwitter(c.getString(c.getColumnIndex(KEY_IDTWITTER)));
            user.setIdpessoa(c.getLong(c.getColumnIndex(KEY_IDPESSOA)));
            return user;
        }else{
            Log.i("carregaUser","tem Nada aqui para ver.");
            return null;
        }

    }

    /*
* get single user
*/
    public User getUserTwitter(User userSearch) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USUARIO + " = '" + userSearch.getUsuario()+"'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            User user = new User();
            user.setUsuario((c.getString(c.getColumnIndex(KEY_USUARIO))));
            user.setSenha(c.getString(c.getColumnIndex(KEY_SENHA)));
            user.setTokenTwitter(c.getString(c.getColumnIndex(KEY_TOKEN)));
            user.setSecretTwitter(c.getString(c.getColumnIndex(KEY_SECRET)));
            user.setUserIdTwitter(c.getString(c.getColumnIndex(KEY_IDTWITTER)));
            user.setIdpessoa(c.getLong(c.getColumnIndex(KEY_IDPESSOA)));
            return user;
        }else{
            Log.i("carregaUser","tem Nada aqui para ver.");
            return null;
        }

    }

    /*
 * getting all users
 * */
    public List<User> getAllUserss() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                user.setUsuario((c.getString(c.getColumnIndex(KEY_USUARIO))));
                user.setSenha(c.getString(c.getColumnIndex(KEY_SENHA)));
                user.setTokenTwitter(c.getString(c.getColumnIndex(KEY_TOKEN)));
                user.setSecretTwitter(c.getString(c.getColumnIndex(KEY_SECRET)));
                user.setUserIdTwitter(c.getString(c.getColumnIndex(KEY_IDTWITTER)));
                user.setIdpessoa(c.getLong(c.getColumnIndex(KEY_IDPESSOA)));

                // adding to todo list
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    /*
 * Updating a user
 */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO, user.getUsuario());
        values.put(KEY_SENHA, user.getSenha());
        values.put(KEY_TOKEN, user.getTokenTwitter());
        values.put(KEY_SECRET, user.getSecretTwitter());
        values.put(KEY_IDTWITTER, user.getUserIdTwitter());
        values.put(KEY_IDPESSOA, user.getIdpessoa());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    /*
 * Deleting a user
 */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(user_id) });
    }

    /*
* Creating a user
*/
    public long createLocal(Local local) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDLOCAL,local.getId());
        values.put(KEY_NAME,local.getName());
        values.put(KEY_BAIRRO,local.getBairro());
        values.put(KEY_IMAGEM,local.getImagem());
        values.put(KEY_RATE,local.getRate());
        values.put(KEY_MYRATE,local.getMyRate());
        values.put(KEY_MYCOMENTARIO,local.getMycomentario());


        // insert row
        long local_id = db.insert(TABLE_LOCAL, null, values);

        return local_id;
    }

    public List<Local> getAllPlaces() {
        List<Local> locais = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCAL;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Local local = new Local();
                local.setId(c.getLong(c.getColumnIndex(KEY_IDLOCAL)));
                local.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                local.setBairro(c.getString(c.getColumnIndex(KEY_BAIRRO)));
                local.setImagem(c.getString(c.getColumnIndex(KEY_IMAGEM)));
                local.setRate(c.getDouble(c.getColumnIndex(KEY_RATE)));
                local.setMyRate(c.getDouble(c.getColumnIndex(KEY_MYRATE)));
                local.setMycomentario(c.getString(c.getColumnIndex(KEY_MYCOMENTARIO)));


                // adding to todo list
                locais.add(local);
            } while (c.moveToNext());
        }

        return locais;
    }

    /*
* get single user
*/
    public Local getLocal(Long localId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LOCAL + " WHERE "
                + KEY_IDLOCAL + " = " + localId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            Local local = new Local();
            local.setId(c.getLong(c.getColumnIndex(KEY_IDLOCAL)));
            local.setName(c.getString(c.getColumnIndex(KEY_NAME)));

            local.setName(c.getString(c.getColumnIndex(KEY_BAIRRO)));
            local.setBairro(c.getString(c.getColumnIndex(KEY_IMAGEM)));
            local.setRate(c.getDouble(c.getColumnIndex(KEY_RATE)));
            local.setMyRate(c.getDouble(c.getColumnIndex(KEY_MYRATE)));
            local.setMycomentario(c.getString(c.getColumnIndex(KEY_MYCOMENTARIO)));
            return local;
        }else{
            Log.i("carregaUser","tem Nada aqui para ver.");
            return null;
        }

    }

    public int updateLocal(Local local) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDLOCAL,local.getId());
        values.put(KEY_NAME,local.getName());
        values.put(KEY_BAIRRO,local.getBairro());
        values.put(KEY_IMAGEM,local.getImagem());
        values.put(KEY_RATE,local.getRate());
        values.put(KEY_MYRATE,local.getMyRate());
        values.put(KEY_MYCOMENTARIO,local.getMycomentario());

        // updating row
        return db.update(TABLE_LOCAL, values, KEY_IDLOCAL + " = ?",
                new String[] { String.valueOf(local.getId()) });
    }

    /*
* Deleting a user
*/
    public int deleteLocal(long local_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(TABLE_LOCAL, KEY_IDLOCAL + " = ?",
                new String[]{String.valueOf(local_id)});

        Log.i("visitados", "deletaaaaa--> "+delete);

        return delete;
    }

}
