public class Movie {

    String title;
    int yearOfRelease;
    boolean memberIncluded;
    private double sumOfRating;
    private int quantityOfRating;
    int movieLength;

    int getQuantityOfRating(){
        return quantityOfRating;
    }

    void showMovieDetails(){
        System.out.println("Movie title: " + title);
        System.out.println("Year of release: " + yearOfRelease);
    }

    void rate(double rate){
        sumOfRating += rate;
        quantityOfRating ++;
    }

    double getAverage(){
        return sumOfRating / quantityOfRating;
    }



}