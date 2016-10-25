package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(view, manager);
    }

//    @Test//TODO
//    public void testClearTable() throws SQLException {
//        command.process("clear|users");
//        command.process("ye");
//        command.process("clear|users");
//        command.process("yes");
//        verify(manager).clear("users");//TODO проверить, что выполнено два раза
//        verify(view).write("Таблица 'users' очищена");
//    }

    @Test
    public void testClearTableErrorOneParameter() {
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect command format. The correct format: 'clear|tableName',\n" +
                    "your command: clear", e.getMessage());
        }
    }

    @Test
    public void testClearTableErrorThreeParameters() {
        try {
            command.process("clear|users|zzz");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect command format. The correct format: 'clear|tableName',\n" +
                    "your command: clear|users|zzz", e.getMessage());
        }
    }

    @Test
    public void TestCanProcessClearWithParameters() {
        assertTrue(command.canProcess("clear|users"));
    }

    @Test
    public void TestCanProcessClearWithoutParameters() {
        assertFalse(command.canProcess("clear"));
    }

}
