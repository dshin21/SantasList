package ca.bcit.ass2.shin_lee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChildrenAdaptor extends ArrayAdapter<Child> {

    public ChildrenAdaptor(Context context, ArrayList<Child> children) {
        super(context, 0, children);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);

        Child child = getItem(position);
        String isNaughty = child.isNaughty.equals("1") ? "Y" : "N";
        TextView childFirstName = view.findViewById(R.id.child);
        childFirstName.setText(
                child.getFirstName() + " " + child.getLastName()
                        + "\n" + child.getDOB()
                        + "\n" + child.getStreet() + ", " + child.getCity() + ", " + child.getProvince() + " " + child.getPostalCode() + ", " + child.getCountry()
                        + "\n" + child.getLat() + ", " + child.getLng()
                        + "\n" + isNaughty
        );

        return view;
    }
}
