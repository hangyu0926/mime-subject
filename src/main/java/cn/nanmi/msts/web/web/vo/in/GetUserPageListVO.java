package cn.nanmi.msts.web.web.vo.in;

/**
 * Created by Solo on 2016/11/21.
 */
public class GetUserPageListVO extends GetUserListVO{
  private int startPage;

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public GetUserPageListVO(int startPage) {
        this.startPage = startPage;
    }

    public GetUserPageListVO(GetUserListVO getVO){
        this.setPageNo(getVO.getPageNo());
        this.setPageSize(getVO.getPageSize());
        this.setKeyWords(getVO.getKeyWords());
    }
}
