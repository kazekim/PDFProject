package com.kazekim.bookmark;

import th.co.tnt.FlipbookSoft.BookmarkEntry;
import android.view.View;


public interface BookmarkListListener {
	 public void onDeleteBookmarkClick(View v,BookmarkEntry entry); 
}
