package com.arlen.frame.view.account.model;

import java.io.Serializable;

/**
 * Created by Arlen on 2016/12/21 18:22.
 */
public class Account implements Serializable {
        private static final long serialVersionUID = 1L;
    private  float balance = -1f;
    private  int points = -1;
    private   int myCouponCounts = -1;
    private   int myGrouponCounts = -1;
    private   int waitPayOrderCounts = -1;
    private   int waitCommentOrderCounts = -1;
    private   int waitDeliverOrderCounts = -1;
    private   int waitReceivingOrderCounts = -1;
    private   int unReadMessageCounts = -1;//未读小心数量

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private   String user = null;


        public int getUnReadMessageCounts() {
            return unReadMessageCounts;
        }

        public void setUnReadMessageCounts(int unReadMessageCounts) {
            this.unReadMessageCounts = unReadMessageCounts;
        }

        public int getWaitRefundCounts() {
            return waitRefundCounts;
        }

        public void setWaitRefundCounts(int waitRefundCounts) {
            this.waitRefundCounts = waitRefundCounts;
        }

        int waitRefundCounts = -1;//退款数量

        private String profileBg;

        public Account() {

        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getMyCouponCounts() {
            return myCouponCounts;
        }

        public void setMyCouponCounts(int myCouponCounts) {
            this.myCouponCounts = myCouponCounts;
        }

        public int getMyGrouponCounts() {
            return myGrouponCounts;
        }

        public void setMyGrouponCounts(int myGrouponCounts) {
            this.myGrouponCounts = myGrouponCounts;
        }

        public int getWaitPayOrderCounts() {
            return waitPayOrderCounts;
        }

        public void setWaitPayOrderCounts(int waitPayOrderCounts) {
            this.waitPayOrderCounts = waitPayOrderCounts;
        }

        public int getWaitDeliverOrderCounts() {
            return waitDeliverOrderCounts;
        }

        public void setWaitDeliverOrderCounts(int waitDeliverOrderCounts) {
            this.waitDeliverOrderCounts = waitDeliverOrderCounts;
        }

        public int getWaitReceivingOrderCounts() {
            return waitReceivingOrderCounts;
        }

        public void setWaitReceivingOrderCounts(int waitReceivingOrderCounts) {
            this.waitReceivingOrderCounts = waitReceivingOrderCounts;
        }

        public void setProfileBg(String profileBg) {
            this.profileBg = profileBg;

        }

        public String getProfileBg() {
            return profileBg;
        }

        public int getWaitCommentOrderCounts() {
            return waitCommentOrderCounts;
        }

        public void setWaitCommentOrderCounts(int waitCommentOrderCounts) {
            this.waitCommentOrderCounts = waitCommentOrderCounts;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    ", points=" + points +
                    ", myCouponCounts=" + myCouponCounts +
                    ", myGrouponCounts=" + myGrouponCounts +
                    ", waitPayOrderCounts=" + waitPayOrderCounts +
                    ", waitCommentOrderCounts=" + waitCommentOrderCounts +
                    ", waitDeliverOrderCounts=" + waitDeliverOrderCounts +
                    ", waitReceivingOrderCounts=" + waitReceivingOrderCounts +
                    ", unReadMessageCounts=" + unReadMessageCounts +
                    ", waitRefundCounts=" + waitRefundCounts +
                    ", profileBg='" + profileBg + '\'' +
                    '}';
        }
}
