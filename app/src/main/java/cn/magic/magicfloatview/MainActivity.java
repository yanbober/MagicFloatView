package cn.magic.magicfloatview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.magic.library.MagicFlyLinearLayout;

public class MainActivity extends AppCompatActivity {
    private MagicFlyLinearLayout giftLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giftLinearLayout = (MagicFlyLinearLayout) this.findViewById(R.id.gift_layout);
        giftLinearLayout.addDrawable(R.drawable.favourite_love_blue);
        giftLinearLayout.addDrawable(R.drawable.favourite_love_pink);
        giftLinearLayout.addDrawable(R.drawable.favourite_love_red);
        giftLinearLayout.addDrawable(R.drawable.favourite_love_yellow);

        ((Button)(this.findViewById(R.id.btn))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giftLinearLayout.flying();
            }
        });
    }
}
