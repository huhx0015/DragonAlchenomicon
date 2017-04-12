package com.huhx0015.dragonalchenomicon;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/** DRAGONALCHENOMICON
 * DEVELOPER: Michael Yoon Huh (huhx0015)
 * LAST UPDATED: 9/27/2013 */

// Toaster (pop-up notification) used for debugging purposes.
public class Toaster implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "Selected Recipe Items : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}