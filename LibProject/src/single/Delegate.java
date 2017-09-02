package single;

import controller.BookController;
import controller.BookRequestController;
import controller.BookReviewController;
import controller.ChattingController;
import controller.MemberController;
import controller.RentController;
import controller.RentLogController;

public class Delegate {
	private static Delegate single = null;
	
	public BookController bookCtrl;
	public MemberController memCtrl;
	public RentController rentCtrl;
	public RentLogController rentLogCtrl;
	public BookReviewController bookReviewCtrl;
	public BookRequestController bookRequestCtrl;
	public ChattingController chatCtrl;

	private Delegate() {
		bookCtrl = new BookController();
		memCtrl = new MemberController();
		rentCtrl = new RentController();
		rentLogCtrl = new RentLogController();
		bookReviewCtrl = new BookReviewController();
		bookRequestCtrl = new BookRequestController();
		chatCtrl = new ChattingController();
	}
	
	public static Delegate getInstance(){
		if(single==null) single = new Delegate();
		return single;
	}
}
