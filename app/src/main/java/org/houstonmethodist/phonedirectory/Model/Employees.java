package org.houstonmethodist.phonedirectory.Model;

import android.widget.Toast;

import org.houstonmethodist.phonedirectory.Services.HttpClientUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Employees {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Employee> ITEMS = new ArrayList<Employee>();

    public static void Search(String searchText) throws IOException {
        String path="Search/"+searchText;
        ITEMS = HttpClientUtil.getEmployees(path);
        ITEM_MAP.clear();
        for (Employee e: ITEMS){
            ITEM_MAP.put(e.NetworkId, e);
        }


    }

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Employee> ITEM_MAP = new HashMap<String, Employee>();

    private static void addItem(Employee e) {
        ITEMS.add(e);
        ITEM_MAP.put(e.NetworkId, e);
    }



    public static class Employee {
        public final String NetworkId;
        public final String LastName;
        public final String FirstName;
        public final String Email;
        public final String Phone;
        public final String MiddleInitial;
        public final String BusinessUnit;
        public final String Department;
        public final String DisplayDetail;

        public Employee(String networkId, String lastName, String firstName, String email, String phone, String businessUnit, String department, String middleInitial) {
            this.NetworkId = networkId;
            this.LastName = lastName;
            this.FirstName = firstName;
            this.Email=email;
            this.Phone=phone;
            this.MiddleInitial=middleInitial;
            this.BusinessUnit=businessUnit;
            this.Department=department;
            this.DisplayDetail= GetDetail();
        }

        private String GetDetail(){
            StringBuilder result = new StringBuilder();
            result.append(String.format("%1$s, %2$s %3$s", this.LastName, this.FirstName, this.MiddleInitial));
            result.append(System.getProperty("line.separator"));
            result.append(String.format("NetworkId: %1$s", this.NetworkId));
            result.append(System.getProperty("line.separator"));

            result.append(String.format("Phone: %1$s", this.Phone));
            result.append(System.getProperty("line.separator"));

            result.append(String.format("%1$s, %2$s", this.BusinessUnit, this.Department));
            result.append(System.getProperty("line.separator"));
            return result.toString();
        }

        @Override
        public String toString() {
            return NetworkId;
        }
    }
}
