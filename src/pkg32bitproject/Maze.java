import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Oyun beyaz bir pulun oyun tahtası üzerinde bir uçtan (A1) diğer uça
 * (H8) gitmesini amaçlamaktadır.
 * Oyun başlangıçında oyun tahtası üzerine;
 * A1 konumuna beyaz pul yerleştirilimelidir.
 * Oyunun kurallarını bozmayacak şekilde ramdom olarak 3 ile 9 adet arasında siyah pullar
 * yerleştirilmelidir. Oyun her zaman bitebilmelidir.
 *
 */

public class Maze {

  private boolean failStep = false; // Siyah pullara carpma durumu
  public List<Coordinat> movements = new ArrayList<>(); // Cozum yolumuz
  private static final Character blank = '.'; // Tahtamizdaki bogelerin konumlari
  private static final Character stamp = 'X'; // Pullarımız
  private static final Character start = 'S'; // Baslangic noktasi
  private static final Character end = 'E'; // Bitis noktasi
  private static final int ROW_SIZE = 8; // Satir sayimiz
  private static final int COLUMN_SIZE = 8; // Sutun sayimiz
  private Random random_number = new Random(); // Rastgele sayi uretelim
  private char[][] board = new char[ROW_SIZE][COLUMN_SIZE]; // Tahtamizi belirtelim

  //	private char[][] board = {
  //          {'.', '.', '.', '.', '.', '.', 'x'},
  //          {'.', '.', '.', 'x', '.', '.', '.'},
  //          {'x', '.', '.', 'x', '.', 'x', '.'},
  //          {'.', '.', 'x', '.', 'x', '.', '.'},
  //          {'.', 'x', '.', 'x', '.', 'x', '.'},
  //          {'.', 'x', '.', '.', '.', '.', 'G'}};

  // Parametresiz constructor
  public Maze() {
    int tempRow;
    int tempColumn;

    List<Coordinat> blackStamps = new ArrayList<>();

    int numOfStamp = random_number.nextInt(5) + 3;

    // Tahtayi bos olustur
    for (int i = 0; i < ROW_SIZE; ++i) {
      for (int j = 0; j < COLUMN_SIZE; ++j) board[i][j] = blank;
    }

    // random siyah pul olusturup yerlestir
    for (int i = 0; i < numOfStamp; ++i) {
      tempRow = random_number.nextInt(ROW_SIZE);
      tempColumn = random_number.nextInt(COLUMN_SIZE);

      if (
        (tempRow == 0 && tempColumn == 0) ||
        (tempRow == ROW_SIZE - 1 && tempColumn == COLUMN_SIZE - 1)
      ) --i; else {
        board[tempRow][tempColumn] = stamp;
        blackStamps.add(new Coordinat(tempRow, tempColumn));
      }
    }

    tempRow = random_number.nextInt(6) + 1;
    board[tempRow][tempRow] = stamp;

    board[ROW_SIZE - 1][COLUMN_SIZE - 1] = end;

    System.out.println("Black Stamps : " + blackStamps);
  }

  // Bu methot guncel tahtayi standart outputa basar
  public void showBoard() {
    Character rowCh = 'a';

    for (int i = 0; i < COLUMN_SIZE; ++i) {
      System.out.print(" " + (rowCh++));
    }

    System.out.println();

    for (int i = 0; i < ROW_SIZE; ++i) {
      System.out.print(i + 1);

      for (int j = 0; j < COLUMN_SIZE; ++j) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  // Bu metod recursive yardimci olarak kullanilacak.
  // 4 yonde hareket saglanabilmektedir.
  // Oncelik sirasi asagi-sag-yukari-sol dur.
  public boolean findPath(int row, int column) {
    // Tablo sinir kontrolu
    if (
      row < 0 || row >= ROW_SIZE || column < 0 || column >= COLUMN_SIZE
    ) return false;

    // Bitis noktasi kontrolu
    if (
      row == ROW_SIZE - 1 && column == COLUMN_SIZE - 1 && failStep == true
    ) return true;

    if (board[row][column] == stamp) failStep = true;

    if (board[row][column] != blank) return false;

    board[row][column] = '*';

    if (findPath(row + 1, column + 1) == true) {
      movements.add(new Coordinat(row, column));
      return true;
    }

    // asagi bak
    if (findPath(row + 1, column) == true) {
      movements.add(new Coordinat(row, column));
      return true;
    }

    // saga bak
    if (findPath(row, column + 1) == true) {
      movements.add(new Coordinat(row, column));
      return true;
    }

    // yukari bak
    if (findPath(row - 1, column) == true) {
      movements.add(new Coordinat(row, column));
      return true;
    }

    // sola bak
    if (findPath(row, column - 1) == true) {
      movements.add(new Coordinat(row, column));
      return true;
    }

    board[row][column] = 'h';
    
    return false;
  }

  // recursive wrapper metod
  public boolean solve() {
    boolean status = findPath(0, 0);

    board[0][0] = 'S';

    return status;
  }
}
