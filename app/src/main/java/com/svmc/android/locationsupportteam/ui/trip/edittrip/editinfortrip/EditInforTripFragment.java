package com.svmc.android.locationsupportteam.ui.trip.edittrip.editinfortrip;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.search.SearchPointActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class EditInforTripFragment extends BaseFragment implements View.OnClickListener, MVPEditInforTripFragment.IViewEditInforTrip {

    private Toolbar toolbar;

    private LinearLayout llStartPoint;
    private TextView tvStartPoint;

    private LinearLayout llEndPoint;
    private TextView tvEndPoint;


    private LinearLayout llChooseDate;
    private LinearLayout llStartDate;
    private TextView tvStartDate;

    private LinearLayout llReturnDate;
    private TextView tvReturnDate;

    private TextView btnNextStep;

    private MVPEditInforTripFragment.IPresenterEditInforTrip presenterEditInforTrip;
    private TripInfor tripInfor;

    public static EditInforTripFragment newInstance() {
        EditInforTripFragment editInforTripFragment = new EditInforTripFragment();
        editInforTripFragment.setTAG(Constans.TagFragment.EDIT_INFOR_TRIP_FRAGMENT);
        return editInforTripFragment;
    }

    public static EditInforTripFragment newInstance(TripInfor infor) {
        EditInforTripFragment editInforTripFragment = new EditInforTripFragment();
        editInforTripFragment.tripInfor = infor;
        editInforTripFragment.setTAG(Constans.TagFragment.EDIT_INFOR_TRIP_FRAGMENT);
        return editInforTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_infor_trip_plan;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {
    }

    @Override
    protected void innitView(View view) {
        innitToolbar(view);

        llStartPoint = view.findViewById(R.id.ll_start_point);
        tvStartPoint = view.findViewById(R.id.tv_start_point);

        llEndPoint = view.findViewById(R.id.ll_end_point);
        tvEndPoint = view.findViewById(R.id.tv_end_point);

        llChooseDate = view.findViewById(R.id.ll_choose_date);
        llStartDate = view.findViewById(R.id.ll_start_date);
        tvStartDate = view.findViewById(R.id.tv_start_date);
        llReturnDate = view.findViewById(R.id.ll_return_date);
        tvReturnDate = view.findViewById(R.id.tv_return_date);
        btnNextStep = view.findViewById(R.id.btn_next_step);

        if (tripInfor != null) {
            innitData(tripInfor);
        }
    }

    public void innitData(TripInfor tripInfor) {
        tvStartPoint.setText(
                tripInfor.getStartPoint() != null ? tripInfor.getStartPoint().getName() : getString(R.string.trip_choose_start_point)
        );
        tvEndPoint.setText(
                tripInfor.getEndPoint()== null ? getString(R.string.trip_choose_end_point) : tripInfor.getEndPoint().getName()
        );
        tvStartDate.setText(
                tripInfor.getStartDate() == null ?  CommonUtils.getCurrentDate("dd/MM/yyyy") : tripInfor.getStartDate()
        );
        tvReturnDate.setText(
                tripInfor.getReturnDate() == null ? getString(R.string.trip_choose_return_date) : tripInfor.getReturnDate()
        );
    }

    private void innitToolbar(View view) {
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getString(R.string.create_trip));
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void addEvents() {
        llStartPoint.setOnClickListener(this);
        llEndPoint.setOnClickListener(this);
        llChooseDate.setOnClickListener(this);
        btnNextStep.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_point:
                chooseStartPoint();
                break;
            case R.id.ll_end_point:
                chooseEndPoint();
                break;
            case R.id.ll_choose_date:
                Toast.makeText(getContext(), "choose date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_next_step:
//                TripInfor tripInfor = ((EditTripActivity) getActivity()).getTripInfor();
//                tripInfor.setStartPoint(new Place());
//                tripInfor.setEndPoint(new Place());
//                tripInfor.setStartDate("17/11/96");
//                tripInfor.setReturnDate("20/11/96");
//                tripInfor.setTotalDay(4);
//
//                Place place = new Place();
//                place.setName("tungts");
//
//                TripDetailDay tripDetailDay = new TripDetailDay();
//                tripDetailDay.setPlace(place);
//                List<TripDetailDay> tripDetailDays = new ArrayList<>();
//                tripDetailDays.add(tripDetailDay);
//                tripDetailDays.add(tripDetailDay);
//                tripDetailDays.add(tripDetailDay);
//
//                TripDayInfor tripDayInfor = new TripDayInfor();
//                tripDayInfor.setDetailDays(tripDetailDays);
//                ArrayList<TripDayInfor> tripDayInfors = new ArrayList<>();
//                tripDayInfors.add(tripDayInfor);
//                tripDayInfors.add(tripDayInfor);
//                tripDayInfors.add(tripDayInfor);
//                tripDayInfors.add(tripDayInfor);
//
//                tripInfor.setTripDayInfors(tripDayInfors);
//
//                ((EditTripActivity) getActivity()).setTripInfor(tripInfor);
//                presenterEditInforTrip.validate(tripInfor);
                break;
        }
    }

    private void chooseEndPoint() {
        Intent i = new Intent(getContext(), SearchPointActivity.class);
        startActivityForResult(i, Constans.RequestCode.RC_END_POINT);
    }

    private void chooseStartPoint() {
        Intent i = new Intent(getContext(), SearchPointActivity.class);
        startActivityForResult(i, Constans.RequestCode.RC_START_POINT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), "" + requestCode + " ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MVPEditInforTripFragment.IPresenterEditInforTrip iPresenterEditInforTrip) {
        if (iPresenterEditInforTrip != null) {
            this.presenterEditInforTrip = iPresenterEditInforTrip;
        }
    }

    public void nextStep() {

    }

    public void onErrValide(int errCode) {
        switch (errCode) {
            case ErrCode.TripCode.HAVE_NO_START_POINT:
                ((BaseActivity) getActivity()).showAlert(getString(R.string.notification), "Hãy chọn điểm bắt đầu", null);
                break;
            case ErrCode.TripCode.HAVE_NO_END_POINT:
                ((BaseActivity) getActivity()).showAlert(getString(R.string.notification), "Hãy chọn điểm đến", null);
                break;
            case ErrCode.TripCode.HAVE_NO_START_DATE:
                ((BaseActivity) getActivity()).showAlert(getString(R.string.notification), "Hãy chọn ngày bắt đầu", null);
                break;
            case ErrCode.TripCode.HAVE_NO_RETURN_DATE:
                ((BaseActivity) getActivity()).showAlert(getString(R.string.notification), "Hãy chọn ngày kết thúc", null);
                break;
        }
    }
}
