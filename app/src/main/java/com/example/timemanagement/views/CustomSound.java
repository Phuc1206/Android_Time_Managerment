package com.example.timemanagement.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.timemanagement.R;

public class CustomSound extends Dialog implements View.OnClickListener {

    private ImageButton ibtnNone, ibtnBell, ibtnDrum, ibtnTrumPet, ibtnRain, ibtnClock, ibtnPhone, ibtnMusic;
    private Button btnSoundCancel, btnSoundDone;
    private String selectedSound;

    private ImageButton selectedButton; // Biến theo dõi nút đang được chọn

    public CustomSound(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sound_dialog); // Thay thế R.layout.sound_dialog bằng id của layout của bạn

        // Khởi tạo các thành phần trong layout
        ibtnNone = findViewById(R.id.ibtnNone);
        ibtnBell = findViewById(R.id.ibtnBell);
        ibtnDrum = findViewById(R.id.ibtnDrum);
        ibtnTrumPet = findViewById(R.id.ibtnTrumPet);
        ibtnRain = findViewById(R.id.ibtnRain);
        ibtnClock = findViewById(R.id.ibtnClock);
        ibtnPhone = findViewById(R.id.ibtnPhone);
        ibtnMusic = findViewById(R.id.ibtnMusic);

        btnSoundCancel = findViewById(R.id.btnSoundCancle); // Lưu ý sửa "Cancle" thành "Cancel"
        btnSoundDone = findViewById(R.id.btnSoundDone);

        // Đăng ký sự kiện click cho các ImageButton và Button
        ibtnNone.setOnClickListener(this);
        ibtnBell.setOnClickListener(this);
        ibtnDrum.setOnClickListener(this);
        ibtnTrumPet.setOnClickListener(this);
        ibtnRain.setOnClickListener(this);
        ibtnClock.setOnClickListener(this);
        ibtnPhone.setOnClickListener(this);
        ibtnMusic.setOnClickListener(this);

        btnSoundCancel.setOnClickListener(this);
        btnSoundDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.ibtnNone || viewId == R.id.ibtnBell || viewId == R.id.ibtnDrum ||
                viewId == R.id.ibtnTrumPet || viewId == R.id.ibtnRain || viewId == R.id.ibtnClock ||
                viewId == R.id.ibtnPhone || viewId == R.id.ibtnMusic) {
            handleSoundButtonClick((ImageButton) v);
        } else if (viewId == R.id.btnSoundCancle) {
            dismiss(); // Đóng dialog
        } else if (viewId == R.id.btnSoundDone) {
            dismiss(); // Đóng dialog
        }
    }

    private void handleSoundButtonClick(ImageButton clickedButton) {
        if (selectedButton != null) {
            // Khôi phục màu của nút trước đó
            selectedButton.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        // Thay đổi màu của nút được chọn
        clickedButton.setBackgroundColor(getContext().getResources().getColor(R.color.green));


        // Cập nhật biến theo dõi nút được chọn
        selectedButton = clickedButton;
    }
}
