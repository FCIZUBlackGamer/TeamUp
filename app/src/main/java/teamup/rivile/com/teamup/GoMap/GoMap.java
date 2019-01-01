package teamup.rivile.com.teamup.GoMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;

public class GoMap extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    View intro, help;
    //    MovableFloatingActionButton floatingActionButton;
//    int fabState = 0;
    //    View help;
//    AlertDialog dialog2;
//    LinearLayout cardView;
//
//
//    RelativeLayout now;

    Integer[] imageArray = {R.drawable.ic_menu_manage, R.drawable.ic_location,
            R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};
    String[] textArray = {"clouds", "mark", "techcrunch", "times"};
    View view;
//    MapView mapView;
    Spinner spinner;
    SpinnerAdapter adapter;
    MovableFloatingActionButton movableFloatingActionButton;
    FloatingActionButton civ_filter;

    //    private FilterAdapter mAdapter;
//    FloatingActionButton filterFAB;
//    FloatingActionButton filterCircleImageView;
    SupportMapFragment mapFragment;
    LayoutInflater inflater;
    Drawable filterDrawable, filterFABDrawable;
    ImageView iv_cancel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_go_map, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        movableFloatingActionButton = view.findViewById(R.id.movableFloatingActionButton);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        filterFAB = view.findViewById(R.id.movableFloatingActionButton);
//        filterCircleImageView = view.findViewById(R.id.civ_filter);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();
        // mapView.getMapAsync(this);
        ((DrawerActivity) getActivity()).Hide();
        adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_value_layout, textArray, imageArray);
        spinner.setAdapter(adapter);
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        help = inflater.inflate(R.layout.floating_content, null);

        civ_filter = help.findViewById(R.id.fab_filter);
        filterDrawable = civ_filter.getDrawable();
        filterFABDrawable = movableFloatingActionButton.getDrawable();


//        movableFloatingActionButton.setHelper(this);
        movableFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                RecyclerView locs = help.findViewById(R.id.recyclerView);
                iv_cancel = help.findViewById(R.id.iv_cancel);
                RecyclerView.Adapter adapter; //Todo: get list of locations from Api and pass to 'locs'


                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(help)
                .setCancelable(false);

                final AlertDialog dialog2 = builder.create();
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog2.show();

                civ_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        movableFloatingActionButton.setImageDrawable(filterDrawable);
                        civ_filter.setImageDrawable(filterFABDrawable);

                         filterDrawable = civ_filter.getDrawable();
                         filterFABDrawable = movableFloatingActionButton.getDrawable();

                        //Todo: Reload recycle view data
                        if (help!= null){
                            ViewGroup parent = (ViewGroup) help.getParent();
                            if (parent != null) {
                                parent.removeAllViews();
                            }
                        }
                        dialog2.dismiss();

                    }
                });

                iv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (help!= null){
                            ViewGroup parent = (ViewGroup) help.getParent();
                            if (parent != null) {
                                parent.removeAllViews();
                            }
                        }
                        dialog2.dismiss();
                    }
                });
            }
        });
//        filterFAB.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                movableFloatingActionButton.callOnTouch(movableFloatingActionButton, event);
//                return false;
//            }
//        });
    }

//    @Override
//    public void callFloatingActionButtonOnClick() {
//
//
//
////        Drawable filterDrawable = filterCircleImageView.getDrawable();
////        Drawable filterFABDrawable = filterFAB.getDrawable();
////
////        filterFAB.setImageDrawable(filterDrawable);
////        filterCircleImageView.setImageDrawable(filterFABDrawable);
//
//
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(30.5883084, 31.4831937);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);
        mMap.clear();
        markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location));
        mMap.addMarker(markerOptions);

        LatLng s = new LatLng(-64, 101);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        MarkerOptions m = new MarkerOptions();
        m.position(sydney);
        mMap.clear();
        m.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location));
        mMap.addMarker(m);
//        markerOptions.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.latitude, sydney.longitude), 14f));

//        mMap.animateCamera(CameraUpdateFactory.newLatLng(s));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                intro = inflater.inflate(R.layout.marker_view, null);

                return intro;
            }

            @Override
            public View getInfoContents(Marker marker) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                intro = inflater.inflate(R.layout.marker_view, null);

                return intro;
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
