package com.example.cooper.baseballbuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cooper.baseballbuddy.models.NewsModel;
import com.example.cooper.baseballbuddy.models.connection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*public class feedActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        listView = (ListView) findViewById(R.id.lvNewsFeed);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teams");

        new JSONTask().execute("https://api.fantasydata.net/mlb/v2/JSON/News");


    }
    public class JSONTask extends AsyncTask<String, String, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connect = null;
            HttpURLConnection huc = null;


            try {
                URL url = new URL(params[0]);
                huc = (HttpURLConnection) url.openConnection();
                huc.setRequestProperty("Ocp-Apim-Subscription-Key", "01fb8861e5e8434891609c4f6277a72b");
                huc.connect();
                InputStream stream1 = huc.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream1));


                reader = new BufferedReader(new InputStreamReader(stream1));
                StringBuffer buffer1 = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer1.append(line);
                }

                String finalJson = buffer1.toString();
                connection newConnection = new connection();
                newConnection.getJSONCallAsString(params[0]);
                List<NewsModel> newsModelList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(finalJson);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    NewsModel newsModel = new NewsModel();

                    newsModel.setNewsID(finalObject.getInt("NewsID"));
                    newsModel.setTitle(finalObject.getString("Title"));
                    newsModel.setUpdated(finalObject.getString("Updated"));
                    newsModel.setUrl(finalObject.getString("Url"));
                    newsModel.setContent(finalObject.getString("Content"));
                    newsModel.setSource(finalObject.getString("Source"));
                    newsModel.setTermsOfUse(finalObject.getString("TermsOfUse"));
                    newsModel.setPlayerID(finalObject.getInt("PlayerID"));
                    newsModel.setTeamID(finalObject.getInt("TeamID"));
                    newsModel.setTeam(finalObject.getString("Team"));

                    newsModelList.add(newsModel);

                }

                return newsModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (huc != null) {
                    huc.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<NewsModel> result) {
            super.onPostExecute(result);
            NewsAdapter adapter = new NewsAdapter(getApplicationContext(), R.layout.row, result);
            listView.setAdapter(adapter);
            // textView.setText(result);
        }
    }
    public class NewsAdapter extends ArrayAdapter{
        private List<NewsModel> newsModelList;
        private int resource;
        private LayoutInflater inflater;
        public NewsAdapter(Context context, int resource, List<NewsModel> objects) {
            super(context, resource, objects);
            newsModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                convertView = inflater.inflate(resource,null);
            }
            TextView Headline;
            TextView Content;

            Content = (TextView)convertView.findViewById(R.id.tvContent);
            Headline = (TextView)convertView.findViewById(R.id.tvHeadline);

            //if teamid matches teamID here
            Headline.setText(newsModelList.get(position).getTitle());
            Content.setText(newsModelList.get(position).getContent());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsModelList.get(position).getUrl()));
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
    @Override
    public void onStart() {

        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<Integer,String> retrievedTeams = new HashMap<Integer, String>();

                dataSnapshot.child("teams").child(auth.getCurrentUser().getUid());
                ArrayList<userInformation> goals = new ArrayList<userInformation>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userInformation userTeam = snapshot.getValue(userInformation.class);
                    Integer id = userTeam.teamID;
                    String name = userTeam.teamName;
                    retrievedTeams.put(id,name);
                    goals.add(userTeam);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
    }

}
*/

//BEN's ADDITION BELOW.. v

public class feedActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<String> teamsfeed = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        listView = (ListView) findViewById(R.id.lvNewsFeed);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teams");


    }

    public class newsTask extends AsyncTask<String, String, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connect = null;
            HttpURLConnection huc = null;


            try {
                URL url = new URL(params[0]);
                huc = (HttpURLConnection) url.openConnection();
                huc.setRequestProperty("Ocp-Apim-Subscription-Key", "01fb8861e5e8434891609c4f6277a72b");
                huc.connect();
                InputStream stream1 = huc.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream1));


                reader = new BufferedReader(new InputStreamReader(stream1));
                StringBuffer buffer1 = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer1.append(line);
                }

                String finalJson = buffer1.toString();
                connection newConnection = new connection();
                newConnection.getJSONCallAsString(params[0]);
                List<NewsModel> newsModelList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(finalJson);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    NewsModel newsModel = new NewsModel();

                    newsModel.setNewsID(finalObject.getInt("NewsID"));
                    newsModel.setTitle(finalObject.getString("Title"));
                    newsModel.setUpdated(finalObject.getString("Updated"));
                    newsModel.setUrl(finalObject.getString("Url"));
                    newsModel.setContent(finalObject.getString("Content"));
                    newsModel.setSource(finalObject.getString("Source"));
                    newsModel.setTermsOfUse(finalObject.getString("TermsOfUse"));
                    newsModel.setPlayerID(finalObject.getInt("PlayerID"));
                    newsModel.setTeamID(finalObject.getInt("TeamID"));
                    newsModel.setTeam(finalObject.getString("Team"));
                    int tempID = finalObject.getInt("TeamID");
                    for (String j : teamsfeed) {
                        if (j == Integer.toString(tempID)) {
                            newsModelList.add(newsModel);
                        }


                    }
                }
                return newsModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (huc != null) {
                    huc.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<NewsModel> result) {
            super.onPostExecute(result);
            NewsAdapter adapter = new NewsAdapter(getApplicationContext(), R.layout.row, result);
            listView.setAdapter(adapter);
            // textView.setText(result);
        }
    }

    public class NewsAdapter extends ArrayAdapter {
        private List<NewsModel> newsModelList;
        private int resource;
        private LayoutInflater inflater;

        public NewsAdapter(Context context, int resource, List<NewsModel> objects) {
            super(context, resource, objects);
            newsModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }
            TextView Headline;
            TextView Content;

            Content = (TextView) convertView.findViewById(R.id.tvContent);
            Headline = (TextView) convertView.findViewById(R.id.tvHeadline);

            //if teamid matches teamID here
            Headline.setText(newsModelList.get(position).getTitle());
            Content.setText(newsModelList.get(position).getContent());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsModelList.get(position).getUrl()));
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<Integer, String> retrievedTeams = new HashMap<Integer, String>();

                dataSnapshot.child("teams").child(auth.getCurrentUser().getUid());
                ArrayList<userInformation> goals = new ArrayList<userInformation>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userInformation userTeam = snapshot.getValue(userInformation.class);
                    Integer id = userTeam.teamID;
                    String name = userTeam.teamName;
                    //Toast.makeText(getApplicationContext(), Integer.toString(id), Toast.LENGTH_SHORT).show();
                    if (id != 0) {
                        teamsfeed.add(id.toString());
                    }

                    retrievedTeams.put(id, name);
                    goals.add(userTeam);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
        new newsTask().execute("https://api.fantasydata.net/mlb/v2/JSON/News");
    }

    //BELOW IS PASTED CODE FROM SCOREACTIVITY.
}
