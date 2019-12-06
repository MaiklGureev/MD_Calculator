package com.gureev.md_calculator.ui.maps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gureev.md_calculator.DBHelper;
import com.gureev.md_calculator.MyLocationListener;
import com.gureev.md_calculator.R;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private MapsViewModel MapsViewModel;

    private MapView mapView;
    private MapController mapController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapsViewModel =
                ViewModelProviders.of(this).get(MapsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maps, container, false);


        mapView = (MapView) root.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            double lat, lon;
            String dateTime, expr;

            lat = bundle.getDouble("lat");
            lon = bundle.getDouble("lon");
            dateTime = bundle.getString("dateTime");
            expr =  bundle.getString("expr");

            ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

            GeoPoint geoPoint = new GeoPoint(lat, lon);
            items.add(new OverlayItem(dateTime, expr, geoPoint));


            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        @Override
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                            //do something
                            return true;
                        }

                        @Override
                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return false;
                        }
                    }, root.getContext());

            mOverlay.setFocusItemsOnTap(true);
            mapView.getOverlays().add(mOverlay);

        }

//        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
//
//        GeoPoint geoPoint = new GeoPoint(53.12, 49.91);
//        items.add(new OverlayItem("11:20 30-11-2019", "510 + 250 = 760", geoPoint));
//
//        GeoPoint geoPoint2 = new GeoPoint(MyLocationListener.getMyLatitude() + 0.1, MyLocationListener.getMyLongitude() + 0.1);
//        items.add(new OverlayItem("11:20 30-11-2019", "510 + 250 = 760", geoPoint2));
//
//        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
//                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
//                    @Override
//                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
//                        //do something
//                        return true;
//                    }
//
//                    @Override
//                    public boolean onItemLongPress(final int index, final OverlayItem item) {
//                        return false;
//                    }
//                }, root.getContext());
//
//        mOverlay.setFocusItemsOnTap(true);
//        mapView.getOverlays().add(mOverlay);

        //setOverlays(root.getContext());

        MyLocationNewOverlay cMyLocationOverlay = new MyLocationNewOverlay(mapView);
        cMyLocationOverlay.enableMyLocation();
        cMyLocationOverlay.setEnabled(true);
        mapView.getOverlays().add(cMyLocationOverlay);

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        mapController = (MapController) mapView.getController();
        mapController.setCenter(new GeoPoint(MyLocationListener.getMyLatitude(), MyLocationListener.getMyLongitude()));
        mapController.setZoom(12);

        return root;
    }

    private void setOverlays(Context context) {
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_MAIN, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int exprIndex = cursor.getColumnIndex(DBHelper.expression);
            int resIndex = cursor.getColumnIndex(DBHelper.result);
            int dataTimeIndex = cursor.getColumnIndex(DBHelper.dataTime);
            int latIndex = cursor.getColumnIndex(DBHelper.lat);
            int lonIndex = cursor.getColumnIndex(DBHelper.lon);
            do {
                GeoPoint geoPoint = new GeoPoint(Double.parseDouble(cursor.getString(latIndex)), Double.parseDouble(cursor.getString(lonIndex)));
//                Log.d("geoPoint", Double.parseDouble(cursor.getString(latIndex))+" " +  Double.parseDouble(cursor.getString(lonIndex)) );
                items.add(new OverlayItem(cursor.getString(dataTimeIndex), cursor.getString(exprIndex) + " = " + cursor.getString(resIndex), geoPoint));
//                Log.d("mLog", cursor.getString(exprIndex));

            } while (cursor.moveToNext());
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);

        mOverlay.setFocusItemsOnTap(true);
        mapView.getOverlays().add(mOverlay);

    }

}