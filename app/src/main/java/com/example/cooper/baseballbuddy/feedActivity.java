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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cooper.baseballbuddy.models.NewsModel;
import com.example.cooper.baseballbuddy.models.ScoresModel;
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
import java.util.Calendar;
import java.util.Date;
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
    private ListView newsListView;
    ArrayList<String> teamsfeed = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private ListView scoreListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        newsListView = (ListView) findViewById(R.id.lvNewsFeed);
        HashMap<Integer,String> logoMap = new HashMap<Integer, String>();
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teams");
        scoreListView = (ListView) findViewById(R.id.lvScoreFeed);

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
            newsListView.setAdapter(adapter);
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
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        new scoreTask().execute("https://api.fantasydata.net/mlb/v2/JSON/GamesByDate/2015-JUL-31");
    }

    //BELOW IS PASTED CODE FROM SCOREACTIVITY.
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class scoreTask extends AsyncTask<String, String, List<ScoresModel>> {
        @Override
        protected List<ScoresModel> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connect = null;

            try {
                URL url = new URL(params[0]);
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestProperty("Ocp-Apim-Subscription-Key", "01fb8861e5e8434891609c4f6277a72b");
                connect.connect();
                InputStream stream = connect.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }


                String finalJSON = buffer.toString(); // call is now inside string finalJSON
                if(buffer.toString() == null){

                }
                List<ScoresModel> scoresModelList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(finalJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject finalJSONObject = jsonArray.getJSONObject(i);
                    ScoresModel scoresModel = new ScoresModel();
                    scoresModel.setStatus(finalJSONObject.getString("Status"));
                    scoresModel.setGameID(finalJSONObject.getString("GameID"));
                    scoresModel.setAwayTeam(finalJSONObject.getString("AwayTeam"));
                    scoresModel.setHomeTeam(finalJSONObject.getString("HomeTeam"));
                    scoresModel.setInning(finalJSONObject.getInt("Inning"));
                    scoresModel.setInningHalf(finalJSONObject.getString("InningHalf"));
                    scoresModel.setAwayTeamRuns(finalJSONObject.getInt("AwayTeamRuns"));
                    scoresModel.setHomeTeamRuns(finalJSONObject.getInt("HomeTeamRuns"));
                    scoresModel.setGlobalAwayTeamID(finalJSONObject.getInt("GlobalAwayTeamID"));
                    scoresModel.setGlobalHomeTeamID(finalJSONObject.getInt("GlobalHomeTeamID"));

                    //scoresModel.setAwayLogoURL(logoMap.get(scoresModel.getGlobalAwayTeamID()));
                   // scoresModel.setHomeLogoURL(logoMap.get(scoresModel.getGlobalHomeTeamID()));
                    int tempHomeID = finalJSONObject.getInt("HomeTeamID");
                    int tempAwayID = finalJSONObject.getInt("AwayTeamID");
                    for (String j : teamsfeed) {
                        if (j == Integer.toString(tempHomeID) || j == Integer.toString(tempAwayID)) {
                            scoresModelList.add(scoresModel);
                        }


                    }


                }
                return scoresModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connect != null) {
                    connect.disconnect();
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
        protected void onPostExecute(List<ScoresModel> result) {
            super.onPostExecute(result);
            if (result != null){

                ScoresAdapter adapter = new ScoresAdapter(getApplicationContext(), R.layout.score_row, result);
                scoreListView.setAdapter(adapter);
            }
            else{
                Toast.makeText(getApplicationContext(),"Not able to fetch data.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ScoresAdapter extends ArrayAdapter {
        private List<ScoresModel> scoreModelList;
        private int resource;
        private LayoutInflater inflater;

        public ScoresAdapter(Context context, int resource, List<ScoresModel> objects) {
            super(context, resource, objects);
            scoreModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }
            TextView homeTeamName;
            TextView awayTeamName;
            TextView homeTeamScore;
            TextView awayTeamScore;
            TextView inningTopBot;
            TextView inningNum;
            TextView Status;
            ImageView awayLogo;
            ImageView homeLogo;

            awayLogo = (ImageView)convertView.findViewById(R.id.rowAwayLogo);
            homeLogo = (ImageView)convertView.findViewById(R.id.rowHomeLogo);
            Status = (TextView) convertView.findViewById(R.id.tvStatus);
            homeTeamName = (TextView) convertView.findViewById(R.id.tvHomeTeam);
            awayTeamName = (TextView) convertView.findViewById(R.id.tvAwayTeam);
            homeTeamScore = (TextView) convertView.findViewById(R.id.tvHomeScore);
            awayTeamScore = (TextView) convertView.findViewById(R.id.tvAwayScore);
            inningTopBot = (TextView) convertView.findViewById(R.id.tvInningHalf);
            inningNum = (TextView) convertView.findViewById(R.id.tvInning);
            //    new DownloadImageTask((ImageView) findViewById(R.id.rowAwayLogo))
            //            .execute(scoreModelList.get(position).getAwayLogoURL());
            //    new DownloadImageTask((ImageView) findViewById(R.id.rowHomeLogo))
            //            .execute(scoreModelList.get(position).getHomeLogoURL());

            Status.setText(scoreModelList.get(position).getStatus().toString());
            homeTeamScore.setText(scoreModelList.get(position).getHomeTeamRuns()+"");
            awayTeamScore.setText(scoreModelList.get(position).getAwayTeamRuns()+"");
            inningTopBot.setText(scoreModelList.get(position).getInningHalf().toString());
            inningNum.setText(scoreModelList.get(position).getInning()+"");
            awayTeamName.setText(scoreModelList.get(position).getAwayTeam().toString());
            homeTeamName.setText(scoreModelList.get(position).getHomeTeam().toString());


            scoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(feedActivity.this,advancedScoresActivity.class);
                    intent.putExtra("GameID",scoreModelList.get(position).getGameID());
                    intent.putExtra("GlobalAwayTeamID",scoreModelList.get(position).getGlobalAwayTeamID());
                    intent.putExtra("GlobalHomeTeamID",scoreModelList.get(position).getGlobalHomeTeamID());
                    intent.putExtra("AwayTeamLogoURL", scoreModelList.get(position).getAwayLogoURL());
                    intent.putExtra("HomeTeamLogoURL", scoreModelList.get(position).getHomeLogoURL());

                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    public String getCurrentDate(){//adjusts web address being called to today
        Date today = Calendar.getInstance().getTime();
        String date = today.toString();
        String month = date.substring(4,7);
        month = date.toUpperCase().substring(4,7);
        String year = date.substring(24,28);
        String dayNum = date.substring(8,10);
        String finalResult = "https://api.fantasydata.net/mlb/v2/JSON/GamesByDate/"+year+"-"+month+"-"+dayNum;
        //String apiCall = "https://api.fantasydata.net/mlb/v2/JSON/GamesByDate/"+finalResult;

        //JSONTask test = new JSONTask();// will later implement a workaround for a day where no games are played, i.e. given NULL
        //test.execute(apiCall);
        return finalResult;
    }
    public void initializeLogos(HashMap<Integer,String> given) {
        //Logos have to be in a certain format... ie can't use an SVG.
        // hashmap holds links to all of the team logos, which are then set in ImageViews
        given.put(10000001,"https://upload.wikimedia.org/wikipedia/en/2/20/Los_Angeles_Dodgers_Logo.png" );
        given.put(10000002, "http://i.imgur.com/Oyv9uDi.png");
        given.put(10000003, "http://i.imgur.com/NKhGZ9k.png");
        given.put(10000004, "http://i.imgur.com/LUNnNTv.png");
        given.put(10000005, "http://i.imgur.com/oJM2dGU.png");
        given.put(10000006, "");
        given.put(10000007, "");
        given.put(10000008, "");
        given.put(10000009,"http://i.imgur.com/dvZ0KOY.png" );
        given.put(10000010, "http://i.imgur.com/1KVNgOL.png");
        given.put(10000011, "http://i.imgur.com/MnOe3ri.png");
        given.put(10000012, "http://i.imgur.com/SVj7ren.png");
        given.put(10000013, "http://i.imgur.com/0MJ5nQD.png");
        given.put(10000014, "http://i.imgur.com/bsBcFCN.png");
        given.put(10000015, "http://i.imgur.com/sFh737i.png");
        given.put(10000016, "http://i.imgur.com/W1CrzEG.png");
        given.put(10000017, "http://i.imgur.com/13Jhdjv.png");
        given.put(10000018, "http://i.imgur.com/wYQJY3Q.png");
        given.put(10000019, "https://upload.wikimedia.org/wikipedia/en/c/cc/Orioles_new.PNG");
        given.put(10000020, "https://upload.wikimedia.org/wikipedia/commons/6/61/MinnesotaTwins.PNG");
        given.put(10000021, "http://i.imgur.com/f3fAVdE.png");
        given.put(10000022,"http://i.imgur.com/uvtprfQ.png" );
        given.put(10000023, "http://i.imgur.com/J8uRGYp.png");
        given.put(10000024,"http://i.imgur.com/NY5JhsG.png" );
        given.put(10000025,"http://i.imgur.com/dGv4lIe.png" );
        given.put(10000026,"http://i.imgur.com/dvXNGbY.png" );
        given.put(10000027,"" );
        given.put(10000028,"http://i.imgur.com/pJMG7FC.png" );
        given.put(10000029,"http://i.imgur.com/w8Bfko7.png" );
        given.put(10000030,"http://i.imgur.com/CiWkFr7.png" );
        given.put(10000031,"http://i.imgur.com/xRwXTEg.png" );
        given.put(10000032,"http://i.imgur.com/vtuW8rg.png" );
        given.put(10000033,"https://upload.wikimedia.org/wikipedia/en/8/81/San_Diego_Padres_logo.png" );
        given.put(10000034,"" );
        given.put(10000035,"http://i.imgur.com/jqtDOTy.png" );
    }
}
