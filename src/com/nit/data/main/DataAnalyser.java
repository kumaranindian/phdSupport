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
                    tmpList=(ArrayList) visitorHash.get(movie.getUserId());
                    String movieId=movie.getMovieId().toString();
                    tmpList.add(movieId);
                    visitorHash.put(movie.getUserId(), String.valueOf(tmpList));
                } else {
                    String movieId=movie.getMovieId().toString();
                    tmpList = new ArrayList();
                    tmpList.add(movieId);
                    visitorHash.put(movie.getUserId(),tmpList);
                }

            }
            System.out.println("RATING data" + ratingHash);
            System.out.println("VISITOR data" + visitorHash);

            dataHashTmp.put("RATING", ratingHash);
            dataHashTmp.put("VISITOR", visitorHash);

        } catch (Exception e) {
        }

        return dataHashTmp;
    }

}
