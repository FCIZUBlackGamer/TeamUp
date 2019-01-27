package teamup.rivile.com.teamup.GoMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

    int[] imageArray = {R.drawable.ic_menu_manage, R.drawable.ic_location,
            R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};

    View view;
    Spinner spinner;
    MovableFloatingActionButton movableFloatingActionButton;
    FloatingActionButton civ_filter;

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

        spinner = (Spinner) view.findViewById(R.id.spinner);

        ((DrawerActivity) getActivity()).initNav();
        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();
        // mapView.getMapAsync(this);
        ((DrawerActivity) getActivity()).Hide();
        CustomAdapter mCustomAdapter = new CustomAdapter(getActivity(), imageArray);
        mCustomAdapter.setDropDownViewResource(R.layout.custom_spinner_row);

        spinner.setAdapter(mCustomAdapter);

        movableFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (movableFloatingActionButton.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_projects).getConstantState()) {
//                    movableFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_marker));
//                    Toast.makeText(getActivity(), "changed1", Toast.LENGTH_SHORT).show();
//                } else {
//                    movableFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_projects));
//                    Toast.makeText(getActivity(), "changed2", Toast.LENGTH_SHORT).show();
//                }
                ((DrawerActivity) getActivity()).opOrCl();
                ((DrawerActivity) getActivity()).Nav();
                ((DrawerActivity) getActivity()).actionNav();

            }
        });

    }

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
