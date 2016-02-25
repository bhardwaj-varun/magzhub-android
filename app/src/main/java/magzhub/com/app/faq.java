package magzhub.com.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class faq extends ActionBarActivity {
    TextView ques1,ans1,ques2, ans2,ques3, ans3,ques4, ans4,ques5, ans5,ques6, ans6,ques7, ans7,ques8, ans8,ques9, ans9,ques10, ans10,ques11, ans11,ques12, ans12,
            ques7b,ans7b,ques13, ans13,ques14, ans14,ques15, ans15,ques16, ans16,ques17, ans17,ques18, ans18,ques19, ans19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FAQ");
        toolbar.setLogo(R.mipmap.ic_launcher_magzhub_transparent_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textviewsSetting();

    }

    public void settingText(){
        ques1.setText("Q.I do not know anything about Magzhub. What is it?");
        ans1.setText("Magzhub is a world class digital magazine store and newsstand for smart devices and web; both digital and mobile." +
        " On Magzhub, you can access most of the leading magazines, the not-so-popular magazines and even a few free magazines." +
                " Magzhub works on subscription model, you need to subscribe to Magzhub monthly or annual subscription to avail the service." );
        ques2.setText("Q.What does Magzhub have for readers?");
        ans2.setText("Magzhub gives readers globally an all new e-reading experience.\n\tWith an easy-to-use interface, it provides interactive browsing and reading, allowing readers to glance through and subscribe \n\t to their most favorite magazines through a single window. Readers can access local magazines \n\t (i.e. magazines from their region) as well as international magazines. \\n\"");
        ques3.setText("Q.What does Magzhub have for publishers?");
        ans3.setText("Magzhub offers a profitable business model for publishers, enabling them to increase their circulation and profit without too much hardship.They do not need to invest much towards this as they sell only e-copies of their magazines through Magzhub.");
        ques4.setText("Q.How can I get started with Magzhub?");
        ans4.setText("There is a simple registration process within Magzhub. Just fill in the required information and get into it.");
        ques5.setText("Q.I love using Magzhub. But I can't find my favourite magazine here. What can I do now?");
        ans5.setText("If you cannot find your favourite magazine on Magzhub, please contact the publisher of the magazine and express your interest to have it on Magzhub's newsstand. You can also mail us on contact@magzhub.com.We have publisher- friendly business policies and we accept any number of publishers to register.");
        ques6.setText("Q.I have the print subscription for a magazine. Do I have to pay separately to Magzhub for subscribing to read it ?");
        ans6.setText("The digital subscription is different from the print subscription. Therefore, you have to subscribe through Magzhub to read a magazine in it. Subscribing to Magzhub is free. However, you can consult with your publisher to cancel the print subscription and then get a digital subscription from Magzhub.");
        ques7.setText("Q.Can I get the PDF version for a magazine I purchased?");
        ans7.setText("No. You can access your digital magazine only through the Magzhub. You can’t get there PDF versions.\n" );
        ques7b.setText("Q.How many times can I view the magazines I have subscribed?");
        ans7b.setText("There are no time limitations once you have subscribed a magazine on Magzhub. You can read your magazines any number of times.");
        ques8.setText("Q.How many times can I view the magazines I have subscribed?");
        ans8.setText("There are no time limitations once you have subscribed a magazine on Magzhub. You can read your magazines any number of times.");
        ques9.setText("Q.Is it safe for me to subscribe a magazine through Magzhub?");
        ans9.setText("You should never worry about the credit card information you enter on Magzhub. We provide the best available security for online financial transactions done through our website. However, we request you to keep your account information safe and counter-check your bank account on the money spent on magazines you buy via Magzhub.");
        ques10.setText("Q.For how many days does my purchased magazine copy remain in my shelf? Does it remain forever; say for example, 36 months? Or is there any time limit?");
        ans10.setText("Once you have subscribed a magazine (single issue or a subscription); it will always be in your account. There is no time limit.");
        ques11.setText("Q.How do I make a payment on Magzhub for a Magazine?");
        ans11.setText("Magzhub offers payments via: Net-Banking & Debit Card and present. Very soon we will be adding Credit Cards payments as well. All card details remain confidential and private. Magzhub uses PayU payment gateway for payments which use SSL encryption technology to protect your card information. Please refer to Magzhub.com ");
        ques12.setText("Q.Are there any hidden charges when I make a purchase digital content on Magzhub?");
        ans12.setText("There are absolutely no hidden charges when you make a subscriptions through Magzhub. You just need to subscribe to our monthly or annual plan and enjoy your favorite magazines & books on the go. Our plans are devised in a manner that you just need to pay once in a month and enjoy whenever you want. During your transaction phase, you will find the Order Id and the Amount. While you will receive a confirmation of your order via email, you are requested to also take note of it when making a transaction.");
        ques13.setText("Q.Is there a purchase limit?");
        ans13.setText("No there are no purchase limits.");
        ques14.setText("Q.You have Cash on Delivery?");
        ans14.setText(" No. We do not have C.O.D as a payment option since all the content on Magzhub.com is Digital.");
        ques15.setText("Q.Is there an expiration or limit to how many times I can view my publications?");
        ans15.setText("No. There is no expiration and no limit to how many times you can view your digital content once you have subscribed them. ");
        ques16.setText("Q.After a purchase, how long will it take for my Digital Content to be available?");
        ans16.setText("With Magzhub, you will get the digital content in your library instantly. ");
        ques17.setText("Q.I forgot my login password. Can you help?");
        ans17.setText("Please follow the steps below to recover your password:1.Open www.Magzhub.com 2.Click Log In on the top of the page 3.Click Forgot your password in the popup that opens up 4.Enter the email address that you used to setup the account and click submit 5.Your password will be sent to your email address.");
        ques18.setText("Q.If I subscribe now, do I get access to old magazines as well?");
        ans18.setText("You will only be able to read the issues that are published during your subscription period. If you would like to read older issues, you need to subscribe them separately");
        ques19.setText("Q.I want to change my User ID. Is it possible?");
        ans19.setText("Please contact our support team at contact@magzhub.com and our support executives will assist you with changing your User ID. ");

    }
    public void onClick1(View v){
            ans1.setVisibility(ans1.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
    }
    public void onClick2(View v){
        ans2.setVisibility(ans2.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
    }
    public void onClick3(View v){
        ans3.setVisibility(ans3.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick4(View v){
        ans4.setVisibility(ans4.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick5(View v){
        ans5.setVisibility(ans5.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick6(View v){
        ans6.setVisibility(ans6.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick7(View v){
        ans7.setVisibility(ans7.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick7b(View v){
        ans7b.setVisibility(ans7b.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick8(View v){
        ans8.setVisibility(ans8.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick9(View v){
        ans9.setVisibility(ans9.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick10(View v){
        ans10.setVisibility(ans10.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick11(View v){
        ans11.setVisibility(ans11.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick12(View v){
        ans12.setVisibility(ans12.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick13(View v){
        ans13.setVisibility(ans13.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick14(View v){
        ans14.setVisibility(ans14.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick15(View v){
        ans15.setVisibility(ans15.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick16(View v){
        ans16.setVisibility(ans16.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick17(View v){
        ans17.setVisibility(ans17.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick18(View v){
        ans18.setVisibility(ans18.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
    public void onClick19(View v){
        ans19.setVisibility(ans19.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }



    /*ques1.setText("1.I do not know anything about Magzhub. What is it?\n");

        ques1.setText("1.I do not know anything about Magzhub. What is it?\n " +
                "\tMagzhub is a world class digital magazine store and newsstand for smart devices and web; both digital and mobile." +
                " On Magzhub, you can access most of the leading magazines, the not-so-popular magazines and even a few free magazines." +
                " Magzhub works on subscription model, you need to subscribe to Magzhub monthly or annual subscription to avail the service." +
                "\n\n2.What does Magzhub have for readers?\n \tMagzhub gives readers globally an all new e-reading experience. " +
                "With an easy-to-use interface, it provides interactive browsing and reading, allowing readers to glance through and subscribe " +
                "to their most favorite magazines through a single window. Readers can access local magazines " +
                "(i.e. magazines from their region) as well as international magazines. \n" +
                "\n3.What does Magzhub have for publishers?\n"+"\t Magzhub offers a profitable business model for publishers, enabling them to increase their circulation and profit without too much hardship. " +
                "They do not need to invest much towards this as they sell only e-copies of their magazines through Magzhub.\n"+
                "\n4.How can I get started with Magzhub?"+
        "\tThere is a simple registration process within Magzhub. Just fill in the required information and get into it.\n"+
        "\n5.I love using Magzhub. But I can't find my favourite magazine here. What can I do now?\n"+"\tIf you cannot find your favourite magazine on Magzhub, please contact the publisher of the magazine and express your interest to have it on Magzhub's newsstand. You can also mail us on contact@magzhub.com. " +
                "We have publisher- friendly business policies and we accept any number of publishers to register.\n"+
                "\n6.I have the print subscription for a magazine. Do I have to pay separately to Magzhub for subscribing to read it ?\n"+"" +
                "\tThe digital subscription is different from the print subscription. Therefore, you have to subscribe through Magzhub to read a magazine in it. Subscribing to Magzhub is free. " +
                "However, you can consult with your publisher to cancel the print subscription and then get a digital subscription from Magzhub.\n"+
        "\n7.Can I get the PDF version for a magazine I purchased?\n"+"" +
                "\tNo. You can access your digital magazine only through the Magzhub. You can’t get there PDF versions.\n"+"\n8.How many times can I view the magazines I have subscribed?\n"+"" +
                "There are no time limitations once you have subscribed a magazine on Magzhub. You can read your magazines any number of times.\n"+
                "\n9.Is it safe for me to subscribe a magazine through Magzhub?\n"+"" +
                "\tYou should never worry about the credit card information you enter on Magzhub. We provide the best available security for online financial transactions done through our website." +
                " However, we request you to keep your account information safe and counter-check your bank account on the money spent on magazines you buy via Magzhub.\n"+"" +
                "\n10.For how many days does my purchased magazine copy remain in my shelf? Does it remain forever; say for example, 36 months? Or is there any time limit?\n"+"" +
                " \tOnce you have subscribed a magazine (single issue or a subscription); it will always be in your account. There is no time limit."+"" +
                "\n11.How do I make a payment on Magzhub for a Magazine?"+"" +
                " \tMagzhub offers payments via: Net-Banking & Debit Card and present. Very soon we will be adding Credit Cards payments as well. All card details remain confidential and private. Magzhub uses PayU payment gateway for payments which use SSL encryption technology to protect your card information.\n"+"" +
                "\n12.Are there any hidden charges when I make a purchase digital content on Magzhub?\n"+"" +
                "\tThere are absolutely no hidden charges when you make a subscriptions through Magzhub. You just need to subscribe to our monthly or annual plan and enjoy your favorite magazines & books on the go. Our plans are devised in a manner that you just need to pay once in a month and enjoy whenever you want. During your transaction phase, you will find the Order Id and the Amount. While you will receive a confirmation of your order via email, you are requested to also take note of it when making a transaction.\n "+"" +
                "\n13.Is there a purchase limit?\n"+"" +
                "\t No there are no purchase limits.\n"+"\n14.You have Cash on Delivery?"+"\n\t No. We do not have C.O.D as a payment option since all the content on Magzhub.com is Digital.\n"+"" +
                        "\n14.Is there an expiration or limit to how many times I can view my publications?"+"\n\tNo. There is no expiration and no limit to how many times you can view your digital content once you have subscribed them. \n"+"" +
                        "\n15.For how many days will my purchased digital content be available in my shelf? Does it remain forever or is there any time limit?\n"+"" +
                        "\t When you subscribe anything on Magzhub (single issue or a complete magazine), it will be in your account always (there is no time limit). \n"+"" +
                        "\n16.After a purchase, how long will it take for my Digital Content to be available?\n"+"" +
                        "\t With Magzhub, you will get the digital content in your library instantly. \n"+"" +
                        "\n17.I forgot my login password. Can you help?\n"+"\tPlease follow the steps below to recover your password:\n" +
                        "                        \tOpen www.Magzhub.com\n" +
                        "                        \tClick Log In on the top of the page\n" +
                        "                        \tClick Forgot your password in the popup that opens up\n" +
                        "                        \tEnter the email address that you used to setup the account and click submit\n" +
                        "                        \tYour password will be sent to your email address.\n"+
                "\n18.I want to change my password. Can you help?\n"+"" +
                        "\tPlease follow the steps below to recover your password:\n" +
                        "                        \tOpen to www.Magzhub.com\n" +
                        "                        \tClick Log In on the top of the page\n" +
                        "                        \tEnter your email address and password and click login\n" +
                        "                        \tClick on the account name which is displayed next to My Shelf\n" +
                        "                        \tClick on the Change Password on the top right of the page.\n" +
                        "                        \tYou can now setup your desired password.\n"+"" +
                        "\n19.If I subscribe now, do I get access to old magazines as well?\n"+"" +
                        "\tYou will only be able to read the issues that are published during your subscription period. If you would like to read older issues, you need to subscribe them separately\n"+"" +
                        "\n20.>How do I view the full page of my magazine without seeing the thumbnails at the bottom of the screen?\n"+"" +
                        "\t Once the magazine is open, you will see the thumbnails of the pages at the bottom. All you need to do is tap anywhere on the page; this will hide the thumbnails and you will see the magazine page on your full screen.\n"+"" +
                        "\n21.I want to change my User ID. Is it possible?\n"+"" +
                        "\t Please contact our support team at contact@magzhub.com and our support executives will assist you with changing your User ID. \n"+""

        );
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faq, menu);
        return true;
    }
    public void textviewsSetting(){
        ques1=(TextView)findViewById(R.id.ques1);
        ans1=(TextView)findViewById(R.id.ans1);
        ques2=(TextView)findViewById(R.id.ques2);
        ans2=(TextView)findViewById(R.id.ans2);
        ques3=(TextView)findViewById(R.id.ques3);
        ans3=(TextView)findViewById(R.id.ans3);
        ques4=(TextView)findViewById(R.id.ques4);
        ans4=(TextView)findViewById(R.id.ans4);
        ques5=(TextView)findViewById(R.id.ques5);
        ans5=(TextView)findViewById(R.id.ans5);
        ques6=(TextView)findViewById(R.id.ques6);
        ans6=(TextView)findViewById(R.id.ans6);
        ques7=(TextView)findViewById(R.id.ques7);
        ans7=(TextView)findViewById(R.id.ans7);
        ques7b=(TextView)findViewById(R.id.ques7b);
        ans7b=(TextView)findViewById(R.id.ans7b);
        ques8=(TextView)findViewById(R.id.ques8);
        ans8=(TextView)findViewById(R.id.ans8);
        ques9=(TextView)findViewById(R.id.ques9);
        ans9=(TextView)findViewById(R.id.ans9);
        ques10=(TextView)findViewById(R.id.ques10);
        ans10=(TextView)findViewById(R.id.ans10);
        ques11=(TextView)findViewById(R.id.ques11);
        ans11=(TextView)findViewById(R.id.ans11);
        ques12=(TextView)findViewById(R.id.ques12);
        ans12=(TextView)findViewById(R.id.ans12);
        ques13=(TextView)findViewById(R.id.ques13);
        ans13=(TextView)findViewById(R.id.ans13);
        ques14=(TextView)findViewById(R.id.ques14);
        ans14=(TextView)findViewById(R.id.ans14);
        ques15=(TextView)findViewById(R.id.ques15);
        ans15=(TextView)findViewById(R.id.ans15);
        ques16=(TextView)findViewById(R.id.ques16);
        ans16=(TextView)findViewById(R.id.ans16);
        ques17=(TextView)findViewById(R.id.ques17);
        ans17=(TextView)findViewById(R.id.ans17);
        ques18=(TextView)findViewById(R.id.ques18);
        ans18=(TextView)findViewById(R.id.ans18);
        ques19=(TextView)findViewById(R.id.ques19);
        ans19=(TextView)findViewById(R.id.ans19);
        settingText();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(faq.this,Setting.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
