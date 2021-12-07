package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ingenioussys.microfinance.Activites.GroupManager.CGT.CGTViewActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;

import java.io.File;
import java.util.List;

public class CGTAdapter extends BaseAdapter {
    Context context;
    List<CGT> cgtList;
    List<Center> CenterList;
    List<Group> GroupList;
    public CGTAdapter(Context context, List<CGT> cgtList) {
        this.context = context;
        this.cgtList = cgtList;
    }

    @Override
    public int getCount() {
        return cgtList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CGT cgt = cgtList.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cgt_item_list, null);

        }

        TextView area_name = view.findViewById(R.id.area_name);
        area_name.setText(cgt.getArea_name()+" - "+cgt.getCenter_name());
        TextView process_name = view.findViewById(R.id.process_name);
        ImageView cgt_image = view.findViewById(R.id.cgt_image);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/CGT"+"/"+cgt.getPicture_path());
        Glide.with(context).load(file).placeholder(R.drawable.ic_survey).into(cgt_image);
        ImageView spinner_item = view.findViewById(R.id.spinner_item);
        process_name.setText("Approved Member "+cgt.getNumber_of_customers());
        spinner_item.setOnClickListener(new myClickListener(i));
        return view;

    }

    class myClickListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        int id;

        public myClickListener(int id) {
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(context, v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.cgt, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() == R.id.Edit)
            {
                ((CGTViewActivity)context).ViewCGTDetail(id);
            }
            else if(menuItem.getItemId() == R.id.delete)
            {

                // ((ViewCenterTransactionActivity)context).ViewGroupDetail(id);

            }

            return true;
        }
    }
}
