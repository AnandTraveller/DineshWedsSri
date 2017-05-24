package com.anandroid.dineshwedssri;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.varunest.sparkbutton.SparkButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 3/13/2017.
 */

public class InvitationFrag extends Fragment {
    private String TAG = InvitationFrag.class.getSimpleName();


    @BindView(R.id.second_lay)
    RelativeLayout second_lay;
    @BindView(R.id.first_lay)
    LinearLayout first_lay;
    @BindView(R.id.imag_right_top)
    ImageView imag_right_top;


    public InvitationFrag() {
        super();
        setRetainInstance(true);
    }

    private static final String CURRENT_TAG = InvitationFrag.class.getSimpleName();
    private Unbinder unbinder;
    public static final int INDEX = 0;
    private Bundle data;
    private int hideIndex = -1;
    protected AppCompatActivity mBaseAct;
    protected Context mBaseCon;
    private InvitationFrag fragment;

    public static InvitationFrag newInstance(Bundle tag) {
        InvitationFrag fragment = new InvitationFrag();
        fragment = fragment;
        Bundle args = tag;
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        mBaseCon = context;
        if ((mBaseCon instanceof AppCompatActivity)) {
            mBaseAct = (AppCompatActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        } else {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View viewRoot = inflater.inflate(R.layout.login_frag, container, false);
        View viewRoot = inflater.inflate(R.layout.invitation, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);
        first_lay.setVisibility(View.GONE);
        second_lay.setVisibility(View.GONE);
        imag_right_top.setVisibility(View.GONE);

        //  cus_id_login.addTextChangedListener(new GenericTextWatcher(cus_id_login));
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Collapse View
        Handler handler_collapse = new Handler();
        handler_collapse.postDelayed(new Runnable() {
            public void run() {

                first_lay.setVisibility(View.VISIBLE);
                second_lay.setVisibility(View.VISIBLE);
                imag_right_top.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.BounceInLeft).duration(3200).repeat(0).playOn(first_lay);
                YoYo.with(Techniques.BounceInRight).duration(3200).repeat(0).playOn(second_lay);
                YoYo.with(Techniques.FadeIn).duration(3200).repeat(0).playOn(imag_right_top);

            }
        }, 4000);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        unbinder = null;
        super.onDetach();
    }


}
