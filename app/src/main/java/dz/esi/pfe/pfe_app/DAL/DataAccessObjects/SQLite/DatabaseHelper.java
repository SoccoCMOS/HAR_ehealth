package dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dz.esi.pfe.pfe_app.DAL.Model.Account;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.Connexion;
import dz.esi.pfe.pfe_app.DAL.Model.FitnessMeasure;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Type;
import dz.esi.pfe.pfe_app.DAL.Model.User;
import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "patientActivity";

    // Table Names
    private static final String TABLE_ACCOUNT = "Account";
    private static final String TABLE_ACTIVITY = "Activity";
    private static final String TABLE_CONNEXION = "Connexion";
    private static final String TABLE_FITNESSMEASURE = "FitnessMeasure";
    private static final String TABLE_MEASUREDATA = "MeasureData";
    private static final String TABLE_MEASURETYPE= "MeasureType";
    private static final String TABLE_USER= "User";
    private static final String TABLE_WINDOWACTIVITY= "WindowActivity";

    // Column Names - Account
    private static final String USERID="User_ID";
    private static final String EMAIL="Email";
    private static final String PASSWORD="Password";

    // Column Names - User
    private static final String NAME="Name";
    private static final String GENDER="Gender";
    private static final String BIRTHDAY="Birthday";
    private static final String BLOODGROUP="Bloodgroup";

    // Column Names - Measure Type
    private static final String CODESENS="CodeSens";
    private static final String LABEL="Label";
    private static final String UNIT="Unit";
    private static final String POSITION="Position";

    // Column Names - Activity
    private static final String CODEACTIVITY="CodeActivity";
    private static final String ACTIVITYLABEL="ActivityLabel";

    // Column Names - Connexion
    private static final String IDCONNEX="IdConnex";
    private static final String STARTCONNEX="StartConnex";
    private static final String FINISHCONNEX="FinishConnex";
    private static final String FREQUENCY="Frequency";

    // Column Names - Fitness Measure
    private static final String IDFM="IdFM";
    private static final String DATE="DateUpdate";
    private static final String WEIGHT="Weight";
    private static final String HEIGHT="Height";
    private static final String WAIST="Waist";

    // Column Names - Measure Data
    private static final String ID="Id";
    private static final String VALUE="Value";
    private static final String TIMESTAMP="Timestamp";

    // Column Names - Window Activity
    private static final String IDWINDOW="IdWindow";
    private static final String START="Start";
    private static final String FINISH="Finish";

    // Table create statements
    private static String CREATE_TABLE_ACCOUNT= "CREATE TABLE " + TABLE_ACCOUNT + "(" + USERID + " TEXT PRIMARY KEY, " + EMAIL + " TEXT, "
            + PASSWORD + " TEXT " + ")";

    private static String CREATE_TABLE_ACTIVITY= "CREATE TABLE " + TABLE_ACTIVITY + "(" + CODEACTIVITY + " TEXT PRIMARY KEY, " +
            ACTIVITYLABEL + " TEXT " + ")";

    private static String CREATE_TABLE_MEASURETYPE= "CREATE TABLE " + TABLE_MEASURETYPE + "(" + CODESENS+ " TEXT PRIMARY KEY, " +
            LABEL + " TEXT, " + UNIT + " TEXT, " + POSITION + " TEXT " + ")";

    private static String CREATE_TABLE_USER= "CREATE TABLE " + TABLE_USER + "(" + USERID + " TEXT PRIMARY KEY, " + NAME + " TEXT, "
            + GENDER + " TEXT, " + BIRTHDAY + " DATETIME, " + BLOODGROUP + " TEXT " + ")";

    private static String CREATE_TABLE_CONNEXION= "CREATE TABLE " + TABLE_CONNEXION + "(" + IDCONNEX + " INTEGER PRIMARY KEY, " +
            STARTCONNEX + " DATETIME, " + FINISHCONNEX + " DATETIME, " + FREQUENCY + " INTEGER, " + CODESENS
             + " TEXT, "+ USERID + " TEXT, " + "FOREIGN KEY(" + CODESENS + ") REFERENCES " + TABLE_MEASURETYPE + "(" + CODESENS + "), "
            +  "FOREIGN KEY(" + USERID + ") REFERENCES " + TABLE_USER + "(" + USERID + ")" + ")";

    private static String CREATE_TABLE_FITNESSMEASURE= "CREATE TABLE " + TABLE_FITNESSMEASURE + "(" + IDFM + " INTEGER PRIMARY KEY, " +
            WEIGHT + " REAL, " + HEIGHT + " REAL, " + WAIST + " REAL, " + DATE + " DATETIME, " + USERID + " TEXT, "
            +  "FOREIGN KEY(" + USERID + ") REFERENCES " + TABLE_USER + "(" + USERID + ")" + ")";

    private static String CREATE_TABLE_MEASUREDATA= "CREATE TABLE " + TABLE_MEASUREDATA + "(" + CODESENS + " TEXT, " + ID + " INTEGER, " +
            VALUE + " REAL, " + TIMESTAMP + " DATETIME, " + USERID + " TEXT, " + "FOREIGN KEY(" + USERID + ") REFERENCES " + TABLE_USER + "("
            + USERID + "), " + "PRIMARY KEY(" + CODESENS + "," + ID + ")" +")";

    private static String CREATE_TABLE_WINDOWACTIVITY= "CREATE TABLE " + TABLE_WINDOWACTIVITY + "(" + IDWINDOW + " INTEGER PRIMARY KEY, " +
            START + " DATETIME, " + FINISH + " DATETIME, "+ USERID + " TEXT, " + CODEACTIVITY + " TEXT, "
            +  "FOREIGN KEY(" + USERID + ") REFERENCES " + TABLE_USER + "(" + USERID + ")"
            +  "FOREIGN KEY(" + CODEACTIVITY + ") REFERENCES " + TABLE_ACTIVITY + "(" + CODEACTIVITY + ")" + ")";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_USER);

        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_MEASURETYPE);

        db.execSQL(CREATE_TABLE_CONNEXION);
        db.execSQL(CREATE_TABLE_FITNESSMEASURE);
        db.execSQL(CREATE_TABLE_MEASUREDATA);
        db.execSQL(CREATE_TABLE_WINDOWACTIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop old tables
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USER);

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MEASURETYPE);

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_CONNEXION);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FITNESSMEASURE);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MEASUREDATA);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_WINDOWACTIVITY);

        // create new tables
        onCreate(db);
    }


    /****************  CRUD Operations *********************************/
    ///// ////////////////////////////// Creation Queries //////////////////////////////////////////

    /** Creating an account  **/
    public long createAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID, account.getUserID());
        values.put(EMAIL, account.getEmail());
        values.put(PASSWORD, account.getPassword_hash());

        // insert row
        long account_id = db.insert(TABLE_ACCOUNT, null, values);
        return account_id;
    }

    /** Creating a user  **/
    public long createUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID, u.getUserID());
        values.put(NAME, u.getName());
        values.put(GENDER,u.getGender().toString());
        values.put(BLOODGROUP,u.getBloodGroup().toString());
        values.put(BIRTHDAY,u.getBirthday().toString());

        // insert row
        long row_id = db.insert(TABLE_USER, null, values);
        return row_id;
    }

    /** Creating an activity  **/
    public long createActivity(Activity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODEACTIVITY, activity.getCodeActivity());
        values.put(ACTIVITYLABEL, activity.getActivityLabel());

        // insert row
        long activity_id = db.insert(TABLE_ACTIVITY, null, values);
        return activity_id;
    }

    /** Creating a measure type  **/
    public long createMeasureType(Measure_Type mt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODESENS, mt.getCodeSens());
        values.put(LABEL, mt.getLabel());

        // insert row
        long row_id = db.insert(TABLE_MEASURETYPE, null, values);
        return row_id;
    }

    /** Creating a connexion **/
    public long createWindowActivity(WindowActivity w){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(IDWINDOW,w.getIdWindow());
        values.put(START,w.getStart().toString());
        values.put(FINISH,w.getFinish().toString());
        values.put(CODEACTIVITY,w.getCodeActivity());
        values.put(USERID,w.getUserID());

        long row_id= db.insert(TABLE_WINDOWACTIVITY,null,values);
        return row_id;
    }

    /** Creating a connexion **/
    public long createConnexion(Connexion c){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(IDCONNEX,c.getId());
        values.put(STARTCONNEX,c.getstartConnex().toString());
        values.put(FINISHCONNEX,c.getfinishConnex().toString());
        values.put(FREQUENCY,c.getFrequency());
        values.put(CODESENS,c.getCodeSens());
        values.put(USERID,c.getUserID());

        long row_id= db.insert(TABLE_CONNEXION,null,values);
        return row_id;
    }

    /** Creating a measure data **/
    public long createMeasureData(Measure_Data md){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ID,md.getId());
        values.put(VALUE, md.getValue());
        values.put(TIMESTAMP,md.getTimestamp().toString());
        values.put(CODESENS, md.getCodeSens());
        values.put(USERID, md.getUserID());

        long row_id= db.insert(TABLE_MEASUREDATA,null,values);
        return row_id;
    }

    /** Creating a fitness measure **/
    public long createFitnessMeasure(FitnessMeasure f){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(IDFM,f.getIdFM());
        values.put(DATE, f.getDateUpdate().toString());
        values.put(WEIGHT, f.getWeight());
        values.put(HEIGHT, f.getHeight());
        values.put(WAIST,f.getWaist());
        values.put(USERID,f.getUserID());

        long row_id= db.insert(TABLE_FITNESSMEASURE,null,values);
        return row_id;
    }

    //////   //////////    READ //////////// ///////////////////////////


    /////   ///////////     UPDATE //////////// ////////////////////////

    ////   ////////////     DELETE //////////// ///////////////////////

}
