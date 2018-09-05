package com.hr.superbigbang.coffpril;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int quantity = 0;
    int overprice = 0;
    int priceof1cap = 5;
    int priceof1mbumagofcap = 2;
    int priceofslivki = 1;
    int priceofchoco = 10;
    boolean plusslivki = false;
    TextView quantityTextView = null;
    TextView summaTextView = null;
    TextView nameText = null;
    TextView endText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = findViewById(R.id.kolichestvo);
        summaTextView = findViewById(R.id.Price);
        nameText = findViewById(R.id.NameInput);
        endText = findViewById(R.id.EndText);
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
    }

    public void addcup(View view) {
        if (quantity < 100) quantity++;
        quantityTextView.setText(String.valueOf(quantity));
    }

    public void decrcup(View view) {
        if (quantity != 0) quantity--;
        quantityTextView.setText(String.valueOf(quantity));
    }

    public void submitOrder(View view) {
        String nonameerror = getString(R.string.please_enter_your_name);
        String personName = nameText.getText().toString();
        if (personName.length() == 0) {
            endText.setText(nonameerror);
            endText.setTextColor(Color.MAGENTA);
        } else {
            endText.setTextColor(Color.BLACK);
            outputcheck(view);
            String endtext = personName + getString(R.string.ThanksForPurchase);
            endText.setText(endtext);
            sendtoemail(endtext);
        }
    }

    public void outputcheck(View view) {
        overprice = quantity * (priceof1cap + priceof1mbumagofcap);
        if (setPlusslivki()) {
            overprice += (quantity * priceofslivki);
        }
        if (setPluschoco()) {
            overprice += (quantity * priceofchoco);
        }
        StringBuilder outtext = new StringBuilder(String.valueOf(overprice));
        outtext.append(getString(R.string.rub));
        summaTextView.setText(outtext.toString());
    }

    /*   public String getTextfromTextField(EditText editText) {
   return editText.getText().toString();
       }*/
    public boolean setPlusslivki() {
        CheckBox plusslivkicheckbox = findViewById(R.id.plusslivkichkbox);
        return plusslivkicheckbox.isChecked();
    }

    public boolean setPluschoco() {
        CheckBox pluschococheckbox = findViewById(R.id.pluschocochkbox);
        return pluschococheckbox.isChecked();
    }

    Intent i = new Intent(Intent.ACTION_SEND);

    private void sendtoemail(String textofmail) {
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"superbigbang@yandex.ru"});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_zakaz));
        i.putExtra(Intent.EXTRA_TEXT, textofmail);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}


