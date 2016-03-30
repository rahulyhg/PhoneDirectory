package org.houstonmethodist.phonedirectory;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import org.houstonmethodist.phonedirectory.Adapter.SimpleItemRecyclerViewAdapter;
import org.houstonmethodist.phonedirectory.Model.Employees;
import org.houstonmethodist.phonedirectory.Services.HttpClientUtil;
import java.io.IOException;


/**
 * An activity representing a list of Employees. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EmployeeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EmployeeListActivity extends AppCompatActivity implements SimpleItemRecyclerViewAdapter.OnItemClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    RecyclerView recyclerView;
    TextView searchView;
    Context context;
    SimpleItemRecyclerViewAdapter.OnItemClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        //backwards compatible toolbar for v7-v21
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recycler= findViewById(R.id.employee_list);
        assert recycler != null;
        recyclerView=(RecyclerView) recycler;
        searchView=(TextView) findViewById(R.id.empty_view);
        context=this;
        mListener=this;

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            new getDataTask().execute(query);
        }
        else{
            searchView.setText(R.string.checkWifi);
            String  wifi=HttpClientUtil.getWifiName(this);
            if (wifi.equals("Meth_PWN")){
                searchView.setText(R.string.Search);
                new pingService().execute();
            }
            else {
                searchView.setText(R.string.NoWifi);
            }
        }


        if (findViewById(R.id.employee_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void onItemClick(Employees.Employee item) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(EmployeeDetailFragment.ARG_ITEM_ID, item.NetworkId);
            arguments.putString(EmployeeDetailFragment.ARG_Phone, item.Phone);
            EmployeeDetailFragment fragment = new EmployeeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.employee_detail_container, fragment)
                    .commit();
        } else
        {
            Intent intent = new Intent(context, EmployeeDetailActivity.class);
            intent.putExtra(EmployeeDetailFragment.ARG_ITEM_ID, item.NetworkId);
            intent.putExtra(EmployeeDetailFragment.ARG_Phone, item.Phone);
            context.startActivity(intent);
        }
    }


    private class pingService extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return Employees.Ping();

            } catch (IOException e) {

            }
            return false;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean available) {

        }
    }


    private class getDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... search) {

            // params comes from the execute() call: params[0] is the url.
            try
            {
                Employees.Search(search[0]);
            }
            catch (IOException e)
            {

            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void v) {
            if(Employees.ITEMS==null || Employees.ITEMS.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                searchView.setText(R.string.NoMatch);
            }
            else {
                recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Employees.ITEMS, mListener ));
                searchView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        }
    }


}
