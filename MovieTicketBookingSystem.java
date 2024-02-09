import java.util.ArrayList;
import java.util.Scanner;

// Movie class representing a movie
class Movie {
    private String title;
    private String genre;

    public Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return title + " - " + genre;
    }
}

// Seat class representing a seat in the theater
class Seat {
    private int seatNumber;
    private boolean isReserved;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isReserved = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserveSeat() {
        this.isReserved = true;
    }

    @Override
    public String toString() {
        return "Seat " + seatNumber + (isReserved ? " (Reserved)" : " (Available)");
    }
}

// Theater class representing a theater with movies and seats
class Theater {
    private ArrayList<Movie> movies;
    private ArrayList<Seat> seats;

    public Theater() {
        movies = new ArrayList<>();
        seats = new ArrayList<>();

        // Add some sample movies
        movies.add(new Movie("Inception", "Sci-Fi"));
		movies.add(new Movie("The Shawshank Redemption", "Drama"));
		movies.add(new Movie("The Dark Knight", "Action"));
		movies.add(new Movie("Pulp Fiction", "Crime"));
		movies.add(new Movie("The Godfather", "Crime"));
		movies.add(new Movie("Forrest Gump", "Drama"));
		movies.add(new Movie("The Matrix", "Sci-Fi"));
		movies.add(new Movie("Jurassic Park", "Adventure"));

        // Add some sample seats
        for (int i = 1; i <= 10; i++) {
            seats.add(new Seat(i));
        }
    }

    public void displayMovies() {
        System.out.println("Movies available:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public void displaySeats() {
        System.out.println("Seats available:");
        for (Seat seat : seats) {
            System.out.println(seat);
        }
    }

    public boolean reserveSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber && !seat.isReserved()) {
                seat.reserveSeat();
                return true;
            }
        }
        return false;
    }

	public ArrayList<Movie> getMovies() {
		return movies;
	}
}

public class MovieTicketBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Theater theater = new Theater();

        // Display available movies
        theater.displayMovies();

        // User selects a movie
        System.out.print("Enter the movie number: ");
        int movieNumber = scanner.nextInt();
        Movie selectedMovie = theater.getMovies().get(movieNumber - 1);

        // Display available seats
        theater.displaySeats();

        // User selects a seat
        System.out.print("Enter the seat number: ");
        int seatNumber = scanner.nextInt();

        // Make a reservation
        if (theater.reserveSeat(seatNumber)) {
            System.out.println("Reservation successful!");
            System.out.println("Movie: " + selectedMovie.getTitle());
            System.out.println("Seat Number: " + seatNumber);
        } else {
            System.out.println("Sorry, the seat is already reserved or invalid.");
        }

        scanner.close();
    }
}
