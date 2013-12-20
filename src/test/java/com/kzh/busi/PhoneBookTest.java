package com.kzh.busi;

import com.kzh.busi.phoneBook.action.PhoneBookAction;
import org.junit.Test;

/**
 * User: kzh
 * Date: 13-4-17
 * Time: 上午9:22
 */
public class PhoneBookTest {
    @Test
    public void display()
    {
        System.out.println("this is a new world");
        PhoneBookAction phoneBookAction=new PhoneBookAction();
        phoneBookAction.generatePhoneBookList();
    }
}
