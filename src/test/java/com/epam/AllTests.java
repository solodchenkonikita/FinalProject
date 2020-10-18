package com.epam;

import com.epam.dao.*;
import com.epam.util.FieldValidationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AdministratorTimetableDaoImplTest.class, ClientBookingDaoImplTest.class,
        MasterTimetableDaoImplTest.class, PriceListDaoImplTest.class,
        TimetableDaoImplTest.class, UserDaoImplTest.class, FieldValidationTest.class})
public class AllTests {

    @Test
    public void testMock() {
    }
}
