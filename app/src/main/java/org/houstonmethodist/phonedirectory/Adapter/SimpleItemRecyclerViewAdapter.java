package org.houstonmethodist.phonedirectory.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.houstonmethodist.phonedirectory.Model.Employees;
import org.houstonmethodist.phonedirectory.R;
import java.util.List;

/**
 * Created by TMHMXY8 on 3/30/2016.
 */
public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.SimpleItemRecyclerViewHolder> {

    private final List<Employees.Employee> mValues;
    private final SimpleItemRecyclerViewAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Employees.Employee item);
    }


    public SimpleItemRecyclerViewAdapter(List<Employees.Employee> items,SimpleItemRecyclerViewAdapter.OnItemClickListener listener)
    {
        mValues = items;
        mListener=listener;
    }


    @Override
    public SimpleItemRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_content, parent, false);
        return new SimpleItemRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleItemRecyclerViewHolder holder, int position) {
        holder.setData(position, mListener);
    }

    @Override
    public int getItemCount() {
        int count=0;
        if (mValues!=null) {
            return mValues.size();
        }
        else {
            return count;
        }
    }

    class SimpleItemRecyclerViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Employees.Employee mItem;

        public SimpleItemRecyclerViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        public void setData(int position,final SimpleItemRecyclerViewAdapter.OnItemClickListener listener){
            mItem = mValues.get(position);
            mIdView.setText(mValues.get(position).NetworkId);
            mContentView.setText(mValues.get(position).LastName + ", " + mValues.get(position).FirstName);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mItem);
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
