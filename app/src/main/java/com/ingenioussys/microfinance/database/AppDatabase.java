package com.ingenioussys.microfinance.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ingenioussys.microfinance.Dao.AreaDao;
import com.ingenioussys.microfinance.Dao.BranchAreaDao;
import com.ingenioussys.microfinance.Dao.BranchDao;
import com.ingenioussys.microfinance.Dao.CGTDao;
import com.ingenioussys.microfinance.Dao.CGTProcessDao;
import com.ingenioussys.microfinance.Dao.CenterDao;
import com.ingenioussys.microfinance.Dao.DayDao;
import com.ingenioussys.microfinance.Dao.GroupDao;
import com.ingenioussys.microfinance.Dao.LoanApplicationCashFlowDao;
import com.ingenioussys.microfinance.Dao.LoanApplicationCashFlowTwoDao;
import com.ingenioussys.microfinance.Dao.LoanApplicationDao;
import com.ingenioussys.microfinance.Dao.LoanApplicationDocumentsDao;
import com.ingenioussys.microfinance.Dao.RoleDao;
import com.ingenioussys.microfinance.Dao.SurveyDao;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Branch;
import com.ingenioussys.microfinance.model.BranchArea;
import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.CGTProcess;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Day;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.LoanApplicationCashFlow;
import com.ingenioussys.microfinance.model.LoanApplicationCashTwoFlow;
import com.ingenioussys.microfinance.model.LoanApplicationDocument;
import com.ingenioussys.microfinance.model.Role;
import com.ingenioussys.microfinance.model.Survey;

@Database(entities = {Area.class, Survey.class, CGT.class, Branch.class, Role.class,
        BranchArea.class, CGTProcess.class, Day.class, LoanApplication.class, LoanApplicationCashFlow.class ,LoanApplicationCashTwoFlow.class, LoanApplicationDocument.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract AreaDao areaDao();
    public abstract CGTDao cgtDao();
    public abstract BranchDao branchDao();
    public abstract RoleDao roleDao();
    public abstract BranchAreaDao branchAreaDao();
    public abstract CGTProcessDao cgtProcessDao();
    public abstract DayDao dayDao();
    public abstract LoanApplicationDao loanApplicationDao();
    public abstract LoanApplicationCashFlowDao loanApplicationCashFlowDao();
    public abstract LoanApplicationCashFlowTwoDao loanApplicationCashFlowTwoDao();
    public abstract LoanApplicationDocumentsDao loanApplicationDocumentsDao();
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "MicroFinance")
                    .build();
        }
        return INSTANCE;
    }

}
