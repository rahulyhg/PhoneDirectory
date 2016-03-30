package org.houstonmethodist.phonedirectory;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.houstonmethodist.phonedirectory.Model.Employees;

/**
 * A fragment representing a single Employee detail screen.
 * This fragment is either contained in a {@link EmployeeListActivity}
 * in two-pane mode (on tablets) or a {@link EmployeeDetailActivity}
 * on handsets.
 */
public class EmployeeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "NetworkId";
    public static final String ARG_Phone = "Phone";


    private Employees.Employee mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Employees.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.NetworkId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.employee_Name)).setText(mItem.LastName + ", " + mItem.FirstName);

            ((TextView) rootView.findViewById(R.id.employee_Email)).setText(Html.fromHtml("<a href=\"mailto:" + mItem.Email + "\">" + mItem.Email + "</a>"));

            ((TextView) rootView.findViewById(R.id.employee_Phone)).setText("Phone: " + mItem.Phone);

            ((TextView) rootView.findViewById(R.id.employee_Unit)).setText(mItem.BusinessUnit);

            ((TextView) rootView.findViewById(R.id.employee_Department)).setText(mItem.Department);
        }

        return rootView;
    }
}
