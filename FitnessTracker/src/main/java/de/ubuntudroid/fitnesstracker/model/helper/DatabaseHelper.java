package de.ubuntudroid.fitnesstracker.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "fitnessWeek.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Dao<FitnessWeek, Integer> fitnessWeekDao;

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FitnessWeek.class);

            fitnessWeekDao = getFitnessWeekDao();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, FitnessWeek.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        fitnessWeekDao = null;
    }

    public Dao<FitnessWeek, Integer> getFitnessWeekDao() throws SQLException{
        if (fitnessWeekDao == null) {
            fitnessWeekDao = getDao(FitnessWeek.class);
        }
        return fitnessWeekDao;
    }
}
