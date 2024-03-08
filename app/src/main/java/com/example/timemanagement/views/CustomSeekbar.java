package com.example.timemanagement.views;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.timemanagement.SeekbarValueListener;
import com.example.timemanagement.database.Task;
import com.example.timemanagement.notification.MyService;
import com.example.timemanagement.R;
import com.example.timemanagement.database.Podomoro;

public class CustomSeekbar extends DialogFragment {
    private SeekbarValueListener listener;
    private Button btnSeekDone;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        TextView tvtime,tvbreaks,tvlongbreak,tvshortbreak;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final android.view.View view = inflater.inflate(R.layout.seekbar_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        tvtime = view.findViewById(R.id.tvprogresstime);
        tvlongbreak = view.findViewById(R.id.tvlongbreak);
        tvbreaks = view.findViewById(R.id.tvbreaks);
        tvshortbreak = view.findViewById(R.id.tvshorttime);
        Button btnSeekDone = view.findViewById(R.id.btnSeekDone);
        final SeekBar seekbarTime = view.findViewById(R.id.seekbar_time);
        final SeekBar seekbarShort = view.findViewById(R.id.seekbar_short);
        final SeekBar seekbarLong = view.findViewById(R.id.seekbar_long);
        final SeekBar seekbarBreaks = view.findViewById(R.id.seekbar_breaks);
        seekbarTime.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarTime.getThumb().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarShort.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarShort.getThumb().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarLong.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarLong.getThumb().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarBreaks.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        seekbarBreaks.getThumb().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        final int[] time = {30};
        final int[] breaks = {4};
        final int[] shortbreak={5};
        final int[] longbreak={15};
        // Set max values for each SeekBar
        seekbarTime.setMin(30);
        seekbarTime.setMax(120);  // Max value is set to 120 minutes
        seekbarShort.setMin(5);
        seekbarShort.setMax(10);  // Max value is set to 10 minutes
        seekbarLong.setMin(15);
        seekbarLong.setMax(60);   // Max value is set to 60 minutes
        seekbarBreaks.setMax(12);  // Max value is set to 12 breaks
        seekbarBreaks.setMin(1);
        seekbarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tvtime.setText(String.valueOf(progress));
                time[0] = progress;
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * (seekBar.getProgress() ) / seekBar.getMax();
                tvtime.measure(0,0);
                int txtW = tvtime.getMeasuredWidth();
                int delta = txtW / 2;
                tvtime.setX(seekBar.getX() + thumbPos - delta);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvtime.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvtime.setVisibility(View.INVISIBLE);
            }
        });

        seekbarShort.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress change for short SeekBar if needed
                tvshortbreak.setText(String.valueOf(progress));
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * (seekBar.getProgress() ) / seekBar.getMax();
                tvshortbreak.measure(0,0);
                int txtW = tvshortbreak.getMeasuredWidth();
                int delta = txtW / 2;
                tvshortbreak.setX(seekBar.getX() + thumbPos - delta);
                shortbreak[0]=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvshortbreak.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvshortbreak.setVisibility(View.INVISIBLE);
            }
        });

        seekbarLong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress change for long SeekBar if needed
                tvlongbreak.setText(String.valueOf(progress));
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * (seekBar.getProgress() ) / seekBar.getMax();
                tvlongbreak.measure(0,0);
                int txtW = tvlongbreak.getMeasuredWidth();
                int delta = txtW / 2;
                tvlongbreak.setX(seekBar.getX() + thumbPos - delta);
                longbreak[0]=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvlongbreak.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvlongbreak.setVisibility(View.INVISIBLE);
            }
        });

        seekbarBreaks.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvbreaks.setText(String.valueOf(progress));
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * (seekBar.getProgress() ) / seekBar.getMax();
                tvbreaks.measure(0,0);
                int txtW = tvbreaks.getMeasuredWidth();
                int delta = txtW / 2;
                tvbreaks.setX(seekBar.getX() + thumbPos - delta);
                breaks[0] = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvbreaks.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvbreaks.setVisibility(View.INVISIBLE);
            }
        });

        alert.setView(view);

        btnSeekDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveClick(time[0], shortbreak[0], longbreak[0], breaks[0]);
                // Đóng dialog nếu cần
                getDialog().dismiss();
            }
        });
        return alert.create();
    }

    private void onPositiveClick(int time,int shortbreak,int longbreak, int breaks) {
//        Podomoro podomoro = new Podomoro(time,breaks,shortbreak,longbreak);
//        Intent intent = new Intent(requireActivity().getBaseContext(), MyService.class);
//        Intent i = new Intent(requireActivity().getBaseContext(), TaskEditActivity.class);
//        Bundle bundle= new Bundle();
//        bundle.putSerializable("podomoro",podomoro);
//        intent.putExtras(bundle);
//        i.putExtra("TIME", time);
//        i.putExtra("SHORT_BREAK",shortbreak);
//        i.putExtra("LONG_BREAK",longbreak);
//        i.putExtra("BREAKS", breaks);
//        startActivity(i);
//        requireActivity().startService(intent);
        listener.onValuesSelected(time, shortbreak, longbreak, breaks);
    }
    public void setSeekbarValueListener(SeekbarValueListener listener) {
        this.listener = listener;
    }
}
