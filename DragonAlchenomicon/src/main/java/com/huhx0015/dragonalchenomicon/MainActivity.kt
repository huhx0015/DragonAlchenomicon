package com.huhx0015.dragonalchenomicon

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.squareup.picasso.Picasso

/** DRAGONALCHENOMICON
 * DEVELOPER: Michael Yoon Huh (huhx0015)
 * LAST UPDATED: 5/30/2014  */

// MainActivity class is responsible for initializing the Android instance.
class MainActivity : Activity() {

    /** CLASS VARIABLES ________________________________________________________________________  */

    private var recipeList1: Spinner? = null
    private var recipeList2: Spinner? = null
    private var recipeList3: Spinner? = null
    private var btnGenerate: Button? = null

    internal var mainSong: MediaPlayer? = null // Global variable for the menu song.

    /** ACTIVITY LIFECYCLE FUNCTIONALITY _______________________________________________________  */

    // onCreate(): The initial function that is called when the activity is run. onCreate() only runs
    // when the activity is first started.
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setUpLayout() // Sets up the layout.
        initView()

        mainSong = MediaPlayer.create(this@MainActivity, R.raw.music01) // Sets the song to Music_01.mp3.
        val getPrefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val music = getPrefs.getBoolean("checkbox", true) // Toggles music playback to true.

        // Plays menu music if the music is set to true.
        if (music == true) {
            mainSong?.start()
        } // Begins song file playback.
    }

    // onPause(): This function is called whenever the current activity is suspended or another
    // activity is launched.
    override fun onPause() {
        super.onPause()

        mainSong?.release() // Stops music playback.
        finish() // The activity is terminated at this point.
    }

    // onStop(): This function runs when screen is no longer visible and the activity is in a
    // state prior to destruction.
    override fun onStop() {
        super.onStop()
    }

    // onDestroy(): This function runs when the activity has terminated and is being destroyed.
    // Calls recycleMemory() to free up memory allocation.
    override fun onDestroy() {

        super.onDestroy()
        //recycleMemory(); // Recycles all ImageView and View objects to free up memory resources.
    }

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /** LAYOUT FUNCTIONALITY _______________________________________________________________  */

    private fun initView() {

        //actionBar?.setDisplayHomeAsUpEnabled(true)

        // BOTTOM NAVIGATION VIEW
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setUpLayout() {

        setContentView(R.layout.activity_alchenomicon) // Sets the XML layout file.

        // Sets up the activity background image attributes.
        val da_background = findViewById(R.id.da_background_image) as ImageView // Used for displaying the background image.
        val da_background_img = R.drawable.da_background
        Picasso.with(this).load(da_background_img).into(da_background)

        // Sets up the logo image attributes.
        //val da_logo = findViewById(R.id.da_main_logo) as ImageView // Used for displaying the background image.
        //val da_logo_img = R.drawable.title_logo
        //Picasso.with(this).load(da_logo_img).into(da_logo)

        setUpRecipeSpinners() // Sets up the spinner (drop down) lists.
        setUpButtonListeners() // Sets up the action for the Alchemize button.

    }

    // Sets up the recipe lists for the spinners.
    private fun setUpRecipeSpinners() {

        recipeList1 = findViewById(R.id.recipeItemSpinner1) as Spinner
        recipeList1!!.onItemSelectedListener = Toaster() // Toast message appears on selection.

        recipeList2 = findViewById(R.id.recipeItemSpinner2) as Spinner
        recipeList2!!.onItemSelectedListener = Toaster() // Toast message appears on selection.

        recipeList3 = findViewById(R.id.recipeItemSpinner3) as Spinner
        recipeList3!!.onItemSelectedListener = Toaster() // Toast message appears on selection.

    }

    // Sets up the action for the Alchemize button.
    private fun setUpButtonListeners() {

        val dq8recipe = DQ8DB(this)

        recipeList1 = findViewById(R.id.recipeItemSpinner1) as Spinner
        recipeList2 = findViewById(R.id.recipeItemSpinner2) as Spinner
        recipeList3 = findViewById(R.id.recipeItemSpinner3) as Spinner
        btnGenerate = findViewById(R.id.alchemize) as Button

        // The action that is executed when the Alchemize button is pressed.
        // Creates the toast (pop-up) notification for the recipe items after the Alchemize button has been pressed.
        btnGenerate!!.setOnClickListener {

            // Attempts to open the dq8_recipe.sql file.
            try {

                //String s = sqlRow.getText().toString();
                //long l = Long.parseLong(s); // Converts string s to long.

                dq8recipe.openDQDatabase() // Opens the database.

                // DEBUGGING - TO SEE IF IT WORKS
                val returnedItem = dq8recipe.getItem(5) // Retrieves item name.

                Toast.makeText(this@MainActivity,
                        "Recipe Match : " + returnedItem,
                        Toast.LENGTH_SHORT).show()


                // If the current recipe item in the SQL database matches the one selected in the application...
                if (returnedItem == recipeList1!!.selectedItem.toString()) {

                    // Launch notification
                    Toast.makeText(this@MainActivity,
                            "Recipe Match : " + returnedItem,
                            Toast.LENGTH_SHORT).show()
                }


                /* WORK ON THIS
                        //-----------------------
                        // ADD IN LOOP CHECK FOR if first item matches database item.
                        boolean foundRecipe = false;

                        while (foundRecipe == false) {

                            String returnedItem = dq8recipe.getItem(l); // Retrieves item name.

                            // If the current recipe item in the SQL database matches the one selected in the application...
                            if (returnedItem.equals(String.valueOf(recipeList1.getSelectedItem()))) {

                                // Launch notification
                                Toast.makeText(MainActivity.this,
                                        "Recipe Match : " + returnedItem,+
                                        Toast.LENGTH_SHORT).show();


                                foundRecipe = true; // Exits while loop condition.
                            }


                        }

    */

                //String returnedRecipe1 = dq8recipe.getItem(l);
                //String returnedRecipe2 = dq8recipe.getRecipe(l);
                dq8recipe.close()

                //sqlName.setText(returnedRecipe1);
                //sqlItem.setText(returnedRecipe2);
            } catch (e: Exception) {

                val error = e.toString()

                /*
                        Dialog d = new Dialog();
                        d.setTitle("Dang it!");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                        */
            }

            /*
                    Toast.makeText(MainActivity.this,
                            "Recipe List : " +
                                    "\nRecipe 1 : "+ String.valueOf(recipeList1.getSelectedItem()) +
                                    "\nRecipe 2 : "+ String.valueOf(recipeList2.getSelectedItem()) +
                                    "\nRecipe 3 : "+ String.valueOf(recipeList3.getSelectedItem()),+
                            Toast.LENGTH_SHORT).show();
                            */
        }

    }

    /** MEMORY FUNCTIONALITY _______________________________________________________________  */

    // recycleMemory(): Recycles ImageView and View objects to clear up memory resources prior to
    // Activity destruction.
    private fun recycleMemory() {

        // NullPointerException error handling.
        try {

            // Unbinds all Drawable objects attached to the current layout.
            //dg_images.getInstance().unbindDrawables(findViewById(R.id.dg_splash_layout));
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        // Prints error message.
    }

    companion object {

        private val DB_NAME = "dq8_recipe.db"
    }

}


