package com.example.guavas.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.widget.BaseExpandableListAdapter;

import com.example.guavas.R;

import androidx.annotation.RequiresApi;

/**
 * This adapter class is used for creating expandable list of questions in FAQ Page (View Support).
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    /**
     * The constructor for this class.
     *
     * @param context              the context.
     * @param expandableListTitle  list of FAQ titles.
     * @param expandableListDetail list of the FAQ details.
     */
    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    /**
     * Gets the child object.
     *
     * @param listPosition         the list position.
     * @param expandedListPosition the expanded list position.
     * @return the child object.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return Objects.requireNonNull(this.expandableListDetail.get(this.expandableListTitle.get(listPosition)))
                .get(expandedListPosition);
    }

    /**
     * Get the position of the child.
     *
     * @param listPosition         the list position.
     * @param expandedListPosition the expanded list position.
     * @return the expanded list position.
     */
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    /**
     * Binds the data to the list.
     *
     * @param listPosition         the list position.
     * @param expandedListPosition the expanded list position.
     * @param isLastChild          <code>true</code> if it's the last item in the list.
     * @param convertView          the view to be bound.
     * @param parent               the parent view group.
     * @return the bound view
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    /**
     * Gets the number of data in the list.
     *
     * @param listPosition the list position.
     * @return the number of data in the list.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int getChildrenCount(int listPosition) {
        return Objects.requireNonNull(this.expandableListDetail.get(this.expandableListTitle.get(listPosition)))
                .size();
    }

    /**
     * Gets the title of the list at position.
     *
     * @param listPosition the list position.
     * @return the title of the list at position.
     */
    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    /**
     * Gets the number of items in the list.
     *
     * @return the number of items in the list.
     */
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    /**
     * Gets the Id of the group.
     *
     * @param listPosition the list position.
     * @return the list position.
     */
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    /**
     * Binds data to the list.
     *
     * @param listPosition the list position
     * @param isExpanded   <code>true</code> if the list is expanded.
     * @param convertView  the view to be bound.
     * @param parent       the parent view group.
     * @return the bound view.
     */
    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    /**
     * Makes the Ids unstable.
     *
     * @return <code>false</code>.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Checks if the child selectable.
     *
     * @param listPosition         the list position.
     * @param expandedListPosition the expanded list position.
     * @return <code>true</code> if the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}