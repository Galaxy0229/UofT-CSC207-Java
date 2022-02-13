import java.util.Random;

public class CandyPlayer {

  private static int totalCandy = (new Random()).nextInt(100);
  private int num_Candy;
  private static int num_player;
  private static int turn;
  private int id;


  public CandyPlayer(int num_Candy){
    this.num_Candy = num_Candy;
    id = num_player;
    num_player ++;
  }

  public int getMyCandy(){
    return num_Candy;
  }

  public static int getMountainCandy(){
    return totalCandy;
  }

  public boolean play(int num_give){
    if (id == turn){
      if (num_give <= num_Candy) {
        if (totalCandy <= num_give) {
          totalCandy += num_give;
          num_Candy -= num_give;
        } else {
          totalCandy -= num_give;
          num_Candy += num_give;
        }
        if (turn<num_player-1) {
          turn ++;
        }
        else turn=0;
        return true;
      }
      else {if (turn<num_player-1) {
        turn ++;
      }
      else turn = 0;
        return false;
      }
    } else return false;
  }


  public static int getTurn(){
    return turn;
  }

  public static int getNumberOfPlayers(){
    return num_player;
  }

  public static void setTurn(int turn_set){
    turn = turn_set;
  }

  public static void setNumberOfPlayers(int num_player_set){
    num_player = num_player_set;
  }


}