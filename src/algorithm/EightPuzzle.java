package algorithm;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gyl on 17-4-17.
 */
public class EightPuzzle {
    //9个数据
    private int[] num = new int[9];

    private int depth;

    private EightPuzzle parent;

    public int Fvalue;

    public int[] getNum() {
        return num;
    }

    public void setNum(int[] num) {
        this.num = num;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getFvalue() {
        return Fvalue;
    }

    public void setFvalue(int fvalue) {
        Fvalue = fvalue;
    }

    public EightPuzzle getParent() {
        return parent;
    }

    public void setParent(EightPuzzle parent) {
        this.parent = parent;
    }

    public EightPuzzle() {
    }

    public EightPuzzle(int[] num) {
        this.num = num;
    }

    public EightPuzzle(int[] num, EightPuzzle parent, int depth) {
        this.num = num;
        this.depth = depth;
        this.parent = parent;
    }

    public void countFValue(EightPuzzle target) {
        int[][] length = {
                {0,1,2,1,2,3,2,3,4},
                {1,0,1,2,1,2,3,2,3},
                {2,1,0,3,2,1,4,3,2},
                {1,2,3,0,1,2,1,2,3},
                {2,1,2,1,0,1,2,1,2},
                {3,2,1,2,1,0,3,2,1},
                {2,3,4,1,2,3,0,1,2},
                {3,2,3,2,1,2,1,0,1},
                {4,3,2,3,2,1,2,1,0}};
//        计算当前状态布局与目标布局不同的数码数
//        int diffLocal = 0;
//        for (int i = 0; i < 9; i++) {
//            if (this.num[i] != 0) {
//                if (this.num[i] != target.num[i]) {
//                    diffLocal++;
//                }
//            }
//        }
//        Fvalue = diffLocal + depth;
        int step = 0;
        int[] startstate = new int[9];//存 每个数字的位置 [i] = j 表示 数i的位置是j
        int[] targetstate = new int[9];
        for (int i = 0; i < 9; i++) {//把每个数的 位置 存入数组中
            startstate[this.num[i]] = i;
            targetstate[target.num[i]] = i;
        }
        for (int i = 1; i < 9; i++) {
            step += length[startstate[i]][targetstate[i]];
        }
        Fvalue = step + depth;
    }

    //获取唯一编码
    public int getCode(){
        int code = num[0];
        for (int i = 1; i < 9; i++) {
            code = code * 10 + num[i];
        }
        return code;
    }

    //判断是否达到了目标状态
    public boolean isTarget(EightPuzzle target) {
        return this.getCode() == target.getCode();
    }

    //根据逆序数的关系判断是否可解
    public boolean isSolvable(EightPuzzle target) {
        int reverse = 0; //逆序数 之和
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < i; j++) {
                if (this.num[i] != 0) {
                    if (this.num[j] > this.num[i]) {
                        reverse++;
                    }
                }
                if (target.getNum()[i] != 0 ) {
                    if (target.getNum()[j] > target.getNum()[i]) {
                        reverse++;
                    }
                }
            }
        }
        //判断 逆序数奇偶性是否相同
        if (reverse % 2 == 0){
            return true;
        } else {
            return false;
        }
    }

    //获取0元素的位置
    public int getZeroPosition() {
        for (int position = 0; position < 9; position++) {
            if (this.num[position] == 0) {
                return position;
            }
        }
        return -1;
    }

    //能否向各个方向移动
    public boolean couldMove(Direction direction) {
        switch (direction) {
            case UP:    return this.getZeroPosition() >= 3;
            case DOWN:  return this.getZeroPosition() <= 5;
            case LEFT:  return this.getZeroPosition() % 3 != 0;
            case RIGHT: return this.getZeroPosition() % 3 != 2;
        }
        return false;
    }

    //移动并返回 移动后的状态类
    public EightPuzzle move(Direction direction) {
        EightPuzzle temp = new EightPuzzle(this.getNum().clone(), this, this.getDepth() + 1);
        int position = getZeroPosition();  // 0的当前位置
        int newPosition = -1;  //0的新位置
        switch (direction) {
            case UP:    newPosition = position - 3; break;
            case DOWN:  newPosition = position + 3; break;
            case LEFT:  newPosition = position - 1; break;
            case RIGHT: newPosition = position + 1; break;
        }
        temp.getNum()[position] = temp.getNum()[newPosition];
        temp.getNum()[newPosition] = 0;
        return temp;
    }

    //按九宫格格式输出
    public void print() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 2) {
                System.out.println(this.getNum()[i]);
            } else {
                System.out.print(this.getNum()[i]);
            }
        }
    }

    public ArrayList<int[]> printRoute() {
        ArrayList<int[]> route = new ArrayList<int[]>();
        EightPuzzle temp = this;
        for (int i = 0; temp != null; i++, temp = temp.getParent()) {
            route.add(0,temp.getNum());
            System.out.println("---------it's the "+i+" step-----------");
            temp.print();
        }
        return route;
    }
}

