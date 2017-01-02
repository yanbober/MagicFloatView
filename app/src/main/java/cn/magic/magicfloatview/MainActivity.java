package cn.magic.magicfloatview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import cn.magic.library.MagicFlyLinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MagicFlyLinearLayout mRainLinearLayout;
    private Button mRainButton;

    private MagicFlyLinearLayout mFlyLinearLayout;
    private Button mFlyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlyLinearLayout = (MagicFlyLinearLayout) this.findViewById(R.id.fly_layout);
        mFlyButton = (Button) this.findViewById(R.id.fly_btn);
        mFlyButton.setOnClickListener(this);

        mRainLinearLayout = (MagicFlyLinearLayout) this.findViewById(R.id.gift_layout);
        mRainButton = (Button) this.findViewById(R.id.gift_btn);
        mRainButton.setOnClickListener(this);

        mRainLinearLayout.addDrawable(R.drawable.fly0);
        mRainLinearLayout.addDrawable(R.drawable.fly1);
        mRainLinearLayout.addDrawable(R.drawable.fly2);
        mRainLinearLayout.addDrawable(R.drawable.fly3);
        mRainLinearLayout.addDrawable(R.drawable.fly4);
        mRainLinearLayout.addDrawable(R.drawable.fly5);

        mFlyLinearLayout.addDrawable(R.drawable.favourite_love_blue);
        mFlyLinearLayout.addDrawable(R.drawable.favourite_love_pink);
        mFlyLinearLayout.addDrawable(R.drawable.favourite_love_red);
        mFlyLinearLayout.addDrawable(R.drawable.favourite_love_yellow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fly_btn:
                mFlyLinearLayout.flying();
                break;
            case R.id.gift_btn:
                for (int index=0; index<8; index++) {
                    mRainLinearLayout.flying();
                }
                break;
        }
    }
}
