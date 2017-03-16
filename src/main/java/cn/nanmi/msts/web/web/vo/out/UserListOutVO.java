package cn.nanmi.msts.web.web.vo.out;

import cn.nanmi.msts.web.model.UserDTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by memedai on 2017/1/17.
 */
public class UserListOutVO implements Serializable {

   private ArrayList<UserModelVO>  userList = new ArrayList<>();
    private long totalCount;

    private long count;

    public ArrayList<UserModelVO> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserModelVO> userList) {
        this.userList = userList;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserListOutVO{" +
                "userList=" + userList +
                ", totalCount=" + totalCount +
                ", count=" + count +
                '}';
    }

    public UserListOutVO(ArrayList<UserModelVO> userList, long totalCount, long count ) {
        this.userList = userList;
        this.totalCount = totalCount;
        this.count = count;
    }
}
