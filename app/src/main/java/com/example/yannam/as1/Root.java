package com.example.yannam.as1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Root extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // **********************CODE FROM FIREBASE********************** //

    // Get a reference to our posts
    Firebase postsRef = new Firebase("https://docs-examples.firebaseio.com/web/saving-data/fireblog/posts");

// Attach an listener to read the data at our posts reference

    postsRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            System.out.println(snapshot.getValue());
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }
    });

// Retrieve new posts as they are added to Firebase
    postsRef.addChildEventListener(new ChildEventListener() {
        // Retrieve new posts as they are added to Firebase
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
            System.out.println("Author: " + newPost.get("author"));
            System.out.println("Title: " + newPost.get("title"));
        }

        //... ChildEventListener also defines onChildChanged, onChildRemoved,
        //    onChildMoved and onCanceled, covered in later sections.
    });

    // ....

    // Get the data on a post that has changed
    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        String title = (String) snapshot.child("title").getValue();
        System.out.println("The updated post title is " + title);
    }


    // ....

    // Get the data on a post that has been removed
    @Override
    public void onChildRemoved(DataSnapshot snapshot) {
        String title = (String) snapshot.child("title").getValue();
        System.out.println("The blog post titled " + title + " has been deleted");
    }

// ....

// ....

    dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            // do some stuff once
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
        }
    });

    // If a LISTENER has been called multiple times to a data location, it will be called multiple
    // times for each event and we must detach it multiple times in order to remove it completely.
    dataRef.removeEventListener(originalListener);

    // In some cases it may be useful for a callback to be called once and then immediately removed.
    dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            // do some stuff once
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
        }
    });

    // To set a priority when writing data to Firebase, use the setValue() and include the priority.
    // Get a reference to our users node
    Firebase usersRef = new Firebase("https://docs-examples.firebaseio.com/android/saving-data/fireblog/users");
// Add two new users with numbered priorities
    usersRef.child("dennisplusplus").setValue(new User("Dennis Ritchie", 1941), 3); // The priority is set to 3

    usersRef.child("physicsmarie").setValue(new User("Marie Curie", 1867), 1); // The priority is set to 1

// Add a new user with a string priority
    usersRef.child("doublehelix").setValue(new User("Rosalind Franklin", 1920), "dna"); // The priority is set to the string 'dna'

}
