package com.example.cooper.baseballbuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cooper.baseballbuddy.models.NewsModel;
import com.example.cooper.baseballbuddy.models.ScoresModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by Cooper on 2/21/2017.
 */

public class scoreActivity extends AppCompatActivity {
    HashMap<Integer,String> logoMap = new HashMap<Integer, String>();
    private ListView listView;
    private TextView test;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        initializeLogos(logoMap);
        listView = (ListView) findViewById(R.id.lvScores);

       // test = (TextView) findViewById(R.id.tvTest);
        //call my parse function here, create it just inside of the activity function
        new JSONTask2().execute("https://api.fantasydata.net/mlb/v2/JSON/GamesByDate/2015-JUL-31");

    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
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
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            test.setText(result);
        }
    }

    public class JSONTask2 extends AsyncTask<String, String, List<ScoresModel>> {
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


                String finalJSON = buffer.toString();
                List<ScoresModel> scoresModelList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(finalJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject finalJSONObject = jsonArray.getJSONObject(i);
                    ScoresModel scoresModel = new ScoresModel();
                    scoresModel.setStatus(finalJSONObject.getString("Status"));
                    scoresModel.setGameID(finalJSONObject.getString("GameID"));
/*

                    scoresModel.setSeason(finalJSONObject.getString("Season"));
                    scoresModel.setSeasonType(finalJSONObject.getInt("SeasonType"));

                    scoresModel.setDay(finalJSONObject.getString("Day"));
                    scoresModel.setDateTime(finalJSONObject.getString("DateTime"));
                    */
                    scoresModel.setAwayTeam(finalJSONObject.getString("AwayTeam"));
                    scoresModel.setHomeTeam(finalJSONObject.getString("HomeTeam"));
                   /*
                    scoresModel.setRescheduledGameID(finalJSONObject.getString("RescheduledGameID"));
                    scoresModel.setStadiumID(finalJSONObject.getString("StadiumID"));
                    scoresModel.setChannel(finalJSONObject.getString("Channel"));
                    */
                    scoresModel.setInning(finalJSONObject.getInt("Inning"));
                    scoresModel.setInningHalf(finalJSONObject.getString("InningHalf"));

                    scoresModel.setAwayTeamRuns(finalJSONObject.getInt("AwayTeamRuns"));
                    scoresModel.setHomeTeamRuns(finalJSONObject.getInt("HomeTeamRuns"));

                  /*  scoresModel.setAwayTeamHits(finalJSONObject.getInt("AwayTeamHits"));
                    scoresModel.setHomeTeamHits(finalJSONObject.getInt("HomeTeamHits"));
                    scoresModel.setAwayTeamErrors(finalJSONObject.getInt("AwayTeamErrors"));
                    scoresModel.setHomeTeamErrors(finalJSONObject.getInt("HomeTeamErrors"));
                    scoresModel.setWinningPitcherID(finalJSONObject.getString("WinningPitcherID"));
                    scoresModel.setLosingPitcherID(finalJSONObject.getString("LosingPitcherID"));
                    scoresModel.setSavingPitcherID(finalJSONObject.getString("SavingPitcherID"));
                    scoresModel.setAttendance(finalJSONObject.getString("Attendance"));
                    scoresModel.setAwayTeamProbablePitcherID(finalJSONObject.getString("AwayTeamProbablePithcerID"));
                    scoresModel.setHomeTeamProbablePitcherID(finalJSONObject.getString("HomeTeamProbablyPitcherID"));
                    scoresModel.setOuts(finalJSONObject.getInt("Outs"));
                    scoresModel.setBalls(finalJSONObject.getInt("Balls"));
                    scoresModel.setStrikes(finalJSONObject.getInt("Strikes"));
                    scoresModel.setCurrentPitcherID(finalJSONObject.getString("CurrentPitcherID"));
                    scoresModel.setCurrentHitterID(finalJSONObject.getString("CurrentHitterID"));
                    scoresModel.setAwayTeamStartingPitcherID(finalJSONObject.getString("AwayTeamStartingPitcherID"));
                    scoresModel.setHomeTeamStartingPitcherID(finalJSONObject.getString("HomeTeamStartingPitcherID"));
                    scoresModel.setCurrentPitchingTeamID(finalJSONObject.getString("CurrentPitchingTeamID"));
                    scoresModel.setCurrentHittingTeamID(finalJSONObject.getString("CurrentHittingTeamID"));
                    scoresModel.setPointSpread(finalJSONObject.getInt("PointSpread"));
                    scoresModel.setOverUnder(finalJSONObject.getInt("OverUnder"));
                    scoresModel.setAwayTeamMoneyLine(finalJSONObject.getInt("AwayTeamMoneyLine"));
                    scoresModel.setHomeTeamMoneyLine(finalJSONObject.getInt("HomeTeamMoneyLine"));
                    scoresModel.setForecastTempLow(finalJSONObject.getInt("ForecastTempLow"));
                    scoresModel.setForecastTempHigh(finalJSONObject.getInt("ForecastTempHigh"));
                    scoresModel.setForecastDescription(finalJSONObject.getString("ForecastDescription"));
                    scoresModel.setForecastWindChill(finalJSONObject.getString("ForecastWindChill"));
                    scoresModel.setForecastWindSpeed(finalJSONObject.getInt("ForecastWindSpeed"));
                    scoresModel.setForecastWindDirection(finalJSONObject.getString("ForecastWindDirection"));
                    scoresModel.setRescheduledFromGameID(finalJSONObject.getString("RescheduledFromGameID"));
                    scoresModel.setRunnerOnFirst(finalJSONObject.getBoolean("RunnerOnFirst"));
                    scoresModel.setRunnerOnSecond(finalJSONObject.getBoolean("RunnerOnSecond"));
                    scoresModel.setRunnerOnThird(finalJSONObject.getBoolean("RunnerOnThird"));
                    scoresModel.setAwayTeamStartingPitcher(finalJSONObject.getString("AwayTeamStartingPitcher"));
                    scoresModel.setHomeTeamStartingPitcher(finalJSONObject.getString("HomeTeamStartingPitcher"));
                    scoresModel.setCurrentPitcher(finalJSONObject.getString("CurrentPitcher"));
                    scoresModel.setCurrentHitter(finalJSONObject.getString("CurrentHitter"));
                    scoresModel.setWinningPitcher(finalJSONObject.getString("WinningPitcher"));
                    scoresModel.setLosingPitcher(finalJSONObject.getString("LosingPitcher"));
                    scoresModel.setSavingPitcher(finalJSONObject.getString("SavingPitcher"));
                    scoresModel.setDueUpHitterID1(finalJSONObject.getString("DueUpHitterID1"));
                    scoresModel.setDueUpHitterID2(finalJSONObject.getString("DueUpHitterID2"));
                    scoresModel.setDueUpHitterID3(finalJSONObject.getString("DueUpHitterID3"));
                    scoresModel.setGlobalGameID(finalJSONObject.getString("GlobalGameID"));

*/

                    scoresModel.setGlobalAwayTeamID(finalJSONObject.getInt("GlobalAwayTeamID"));
                    scoresModel.setGlobalHomeTeamID(finalJSONObject.getInt("GlobalHomeTeamID"));
                    scoresModel.setAwayLogoURL(logoMap.get(scoresModel.getGlobalAwayTeamID()));
                    scoresModel.setHomeLogoURL(logoMap.get(scoresModel.getGlobalHomeTeamID()));

                    scoresModelList.add(scoresModel);

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
                listView.setAdapter(adapter);
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

            Status = (TextView) convertView.findViewById(R.id.tvStatus);
            homeTeamName = (TextView) convertView.findViewById(R.id.tvHomeTeam);
            awayTeamName = (TextView) convertView.findViewById(R.id.tvAwayTeam);
            homeTeamScore = (TextView) convertView.findViewById(R.id.tvHomeScore);
            awayTeamScore = (TextView) convertView.findViewById(R.id.tvAwayScore);
            inningTopBot = (TextView) convertView.findViewById(R.id.tvInningHalf);
            inningNum = (TextView) convertView.findViewById(R.id.tvInning);


            //originally, content declared as textview like above
            // Content = (TextView)convertView.findViewById(R.id.tvContent);
            //Headline = (TextView)convertView.findViewById(R.id.tvHeadline);
            Status.setText(scoreModelList.get(position).getStatus().toString());
            homeTeamScore.setText(scoreModelList.get(position).getHomeTeamRuns()+"");
            awayTeamScore.setText(scoreModelList.get(position).getAwayTeamRuns()+"");
            inningTopBot.setText(scoreModelList.get(position).getInningHalf().toString());
            inningNum.setText(scoreModelList.get(position).getInning()+"");
            awayTeamName.setText(scoreModelList.get(position).getAwayTeam().toString());
            homeTeamName.setText(scoreModelList.get(position).getHomeTeam().toString());
            //Headline.setText(newsModelList.get(position).getTitle());
            //Content.setText(newsModelList.get(position).getContent());


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(scoreActivity.this,advancedScoresActivity.class);
                    intent.putExtra("GameID",scoreModelList.get(position).getGameID());
                    intent.putExtra("GlobalAwayTeamID",scoreModelList.get(position).getGlobalAwayTeamID());
                    intent.putExtra("GlobalHomeTeamID",scoreModelList.get(position).getGlobalHomeTeamID());

                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
    public void getCurrentDate(){//adjusts web address being called to today
        Date today = Calendar.getInstance().getTime();
        String date = today.toString();
        String month = date.substring(4,7);
        month = date.toUpperCase().substring(4,7);
        String year = date.substring(24,28);
        String dayNum = date.substring(8,10);
        String finalResult = year+"-"+month+"-"+dayNum;
        String apiCall = "https://api.fantasydata.net/mlb/v2/JSON/GamesByDate/"+finalResult;

    }
    public void initializeLogos(HashMap<Integer,String> given) { //Logos have to be in a certain format... ie can't use an SVG.
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
