/**
 * Tablo uzerindeki koordinatlari tutmak icin kullanilacak
 * java class yapimizdir.
 */
class Coordinat {

  int row;
  int column;

  Coordinat(int row, int column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public String toString() {
    Character chCol = 'a';
    chCol = (char) ((int) chCol + column);
    return row + 1 + " " + chCol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    Coordinat coordinat = (Coordinat) o;

    if (row != coordinat.row)
      return false;

    return column == coordinat.column;
  }
}
