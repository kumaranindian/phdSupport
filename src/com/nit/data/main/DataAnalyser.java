/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nit.data.main;

import com.nit.data.excel.reader.ExcelReader;
import com.nit.data.pojo.Movie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Karthikeyan
 */
public class DataAnalyser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ArrayList<Movie> dataList = new ArrayList<>();
        Hashtable dataHash = new Hashtable();
        String excelFilePath = "C:\\Users\\Karthikeyan\\Documents\\NetBeansProjects\\DataAnalyser\\src\\com\\nit\\data\\resources\\Book1.xlsx";
        ExcelReader excelReader = new ExcelReader();
        dataList = excelReader.readingExcel(excelFilePath);
        dataHash = findRatingAndVisitors(dataList);
    }

    private static Hashtable findRatingAndVisitors(ArrayList dataList) {
        System.out.println("dataList size ==>" + dataList.size());
        Movie movie = new Movie();
        Hashtable dataHashTmp = new Hashtable();
        Hashtable ratingHash = new Hashtable();
        Hashtable visitorHash = new Hashtable();
        ArrayList list = new ArrayList();
        ArrayList tmpList = new ArrayList();
        try {
            for (int i = 0; i < dataList.size(); i++) {
                movie = new Movie();
                movie = (Movie) dataList.get(i);

                if (ratingHash.containsKey(movie.getMovieId())) {
                    int previousRating = Integer.parseInt(ratingHash.get(movie.getMovieId()).toString());
                    int totalRating = previousRating + movie.getMovieRating();
                    ratingHash.put(movie.getMovieId(), String.valueOf(totalRating));
                } else {
                    ratingHash.put(movie.getMovieId(), String.valueOf(movie.getMovieRating()));
                }
                //
//                if (visitorHash.containsKey(movie.getMovieId())) {
//                    int previousCount = Integer.parseInt(visitorHash.get(movie.getMovieId()).toString());
//                    int totalCount = previousCount + 1;
//                    visitorHash.put(movie.getMovieId(), String.valueOf(totalCount));
//                } else {
//                    visitorHash.put(movie.getMovieId(), String.valueOf("1"));
//                }
                if (visitorHash.containsKey(movie.getUserId())) {

                    tmpList = new ArrayList();
                    tmpList = (ArrayList) visitorHash.get(movie.getUserId());
                    String movieId = movie.getMovieId().toString();
                    tmpList.add(movieId);
                    visitorHash.put(movie.getUserId(), tmpList);
                } else {
                    String movieId = movie.getMovieId().toString();
                    tmpList = new ArrayList();
                    tmpList.add(movieId);
                    visitorHash.put(movie.getUserId(), tmpList);
                }

            }
            System.out.println("RATING data" + ratingHash);
            System.out.println("VISITOR data" + visitorHash);

            dataHashTmp.put("RATING", ratingHash);
            dataHashTmp.put("VISITOR", visitorHash);

            findSuggestionForUserId(visitorHash);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataHashTmp;
    }

    private static void findSuggestionForUserId(Hashtable visitorHash) {
        Movie inputMovie = new Movie();
        Scanner sin = new Scanner(System.in);
        ArrayList comparingList = new ArrayList();
        ArrayList inputList = new ArrayList();
        String userId = "";

        try {
//            System.out.println("ENTER USER ID TO FIND THEIR SUGGESTIONS :");
//            userId = sin.next();
//            inputMovie.setUserId(userId);
//            inputList = (ArrayList) visitorHash.get(inputMovie.getUserId());
//            System.out.println(" REQUESTED USER WATCHED LISTED MOVIES(IDs) :" + inputList);

            Set<String> keys = visitorHash.keySet();
            System.out.println("keys 1==>" + keys);
            int i = 0;
            for (String keyInput : keys) {
                inputList = new ArrayList();
                inputList = (ArrayList) visitorHash.get(keyInput);
                for (String key : keys) {
                    if (!key.equalsIgnoreCase(keyInput)) {
                        comparingList = new ArrayList();
                        comparingList = (ArrayList) visitorHash.get(key);
                        if (comparingList.containsAll(inputList)) {
                            System.out.println("REQUESTED USER ' " + keyInput + " ' HAVING SIMILAR INTEREST WITH :::' " + key+" '");
                            comparingList.removeAll(inputList);
                            i++;
                            System.out.println("SUGGESTION FOR REQUESTED USER  " + comparingList);

                        } else {
                            // System.out.println("no match");
                        }
                    }
                    //System.out.println("Value of "+key+" is: "+visitorHash.get(key));
                }
            }
            System.out.println("total combn==>"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
