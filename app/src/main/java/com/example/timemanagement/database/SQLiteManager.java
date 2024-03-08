package com.example.timemanagement.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "TaskDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Task";
    private static final String COUNTER = "Counter";


    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DECS_FIELD = "desc";
    private static final String COLOR_FIELD = "color";
    private static final String DELETE_FIELD = "deleted";
    private static final String DATE_FIELD = "date";
    private static final String TIMES_FIELD = "times";
    private static final String TIME_END_FIELD = "time_end";
    private static final String CATEGORY_FIELD = "category";
    private static final String TIME_FIELD = "time";

    private static final String SHORTBREAK_FIELD = "shortbreak";
    private static final String LONGBREAK_FIELD = "longbreak";
    private static final String BREAKS_FIELD = "break";
    private static final String TASK_PERCENT_FIELD = "task_percent";
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
    public SQLiteManager( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DECS_FIELD)
                .append(" TEXT, ")
                .append(COLOR_FIELD)
                .append(" TEXT, ")
                .append(DELETE_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT, ")
                .append(TIMES_FIELD)
                .append(" TEXT, ")
                .append(TIME_END_FIELD)
                .append(" TEXT,")
                .append(CATEGORY_FIELD)
                .append(" TEXT,")
                .append(TIME_FIELD)
                .append(" TEXT,")
                .append(SHORTBREAK_FIELD)
                .append(" TEXT,")
                .append(LONGBREAK_FIELD)
                .append(" TEXT,")
                .append(BREAKS_FIELD)
                .append(" TEXT, ")
                .append(TASK_PERCENT_FIELD)
                .append(" TEXT) ");
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }

    }
    public void addNoteToDatabase(Task task)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, task.getId());
        contentValues.put(TITLE_FIELD, task.getTitle());
        contentValues.put(DECS_FIELD, task.getDes());
        contentValues.put(COLOR_FIELD,task.getColor());
        contentValues.put(DELETE_FIELD, getStringFromDate(task.getDeleted()));
        contentValues.put(DATE_FIELD, task.getDate().toString());
        contentValues.put(TIMES_FIELD, task.getTimes().toString());
        contentValues.put(TIME_END_FIELD, task.getTime_end().toString());
        contentValues.put(CATEGORY_FIELD, task.getCategory().toString());
        contentValues.put(TIME_FIELD, task.getCategory());
        contentValues.put(SHORTBREAK_FIELD, task.getCategory());
        contentValues.put(LONGBREAK_FIELD, task.getCategory());
        contentValues.put(BREAKS_FIELD, task.getCategory());
        contentValues.put(TASK_PERCENT_FIELD, task.getTaskCompletionPercentage());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateNoteListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String color = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);
                    LocalDate date = LocalDate.parse(result.getString(6));
                    LocalTime times = LocalTime.parse(result.getString(7));
                    LocalTime time_end = LocalTime.parse(result.getString(8));
                    String category = result.getString(9);
                    int time = result.getInt(10);
                    int shortbreak = result.getInt(11);
                    int longbreak = result.getInt(12);
                    int breaks = result.getInt(13);
                    int taskpercent = result.getInt(14);
                    Task task = new Task(id,title,desc,color,deleted,date,times,time_end,category,time,shortbreak,longbreak,breaks,taskpercent);
                    Task.tasksList.add(task);
                }
            }
        }
    }

    public void updateNoteInDB(Task task)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, task.getId());
        contentValues.put(TITLE_FIELD, task.getTitle());
        contentValues.put(DECS_FIELD, task.getDes());
        contentValues.put(COLOR_FIELD,task.getColor());
        contentValues.put(TIMES_FIELD,task.getTimes().toString());
        contentValues.put(TASK_PERCENT_FIELD,task.getTaskCompletionPercentage());
        contentValues.put(DELETE_FIELD, getStringFromDate(task.getDeleted()));
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(task.getId())});
    }

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}
