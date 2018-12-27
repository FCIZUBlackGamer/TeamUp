package teamup.rivile.com.teamup.Department;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.R;


public class FragmentHome extends Fragment {
    // Trend Category
//    RecyclerView recyclerView;
//    RecyclerView.Adapter adapter;
//    List<Category> categoryList;

    // All Category
    GridView gridView;
    DepartmentAdapter departmentAdapter;
    List<Department> categories;
    View view;

    FragmentManager fragmentManager;
    static int catId = -1;

    public static FragmentHome catOrEmployee(int Id){
        FragmentHome home = new FragmentHome();
        catId = Id;
        return home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView = (RecyclerView) view.findViewById(R.id.trend_rec);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(layoutManager);
//        categoryList = new ArrayList<>();

        fragmentManager = getFragmentManager();
        gridView = (GridView) view.findViewById(R.id.gridview);
        categories = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        if (catId != -1){
//            loadEmployees(catId);
//        }else {
//            loadCategorys();
//        }




    }
//
//    /**
//     * http://abdelkreimahmed.000webhostapp.com/ListUsersInCat.php
//     * */
//    private void loadEmployees(final int catId) {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("جارى تحميل الاقسام ...");
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://abdelkreimahmed.000webhostapp.com/ListUsersInCat.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Response",response);
//                progressDialog.dismiss();
//                try {
//                    JSONObject object = new JSONObject(response);
//                    JSONArray array = object.getJSONArray("data");
//                    if (array.length() > 0){
//                        for (int x=0; x<10; x++){
//                            JSONObject object1 = array.getJSONObject(x);
//                            Category category = new Category(object1.getInt("catId"),
//                                    object1.getInt("numUsers"),
//                                    object1.getString("catName"));
//                            category.setRate((float) object1.getDouble("rate"));
//                            categories.add(category);
//                        }
//                        departmentAdapter = new DepartmentAdapter(getActivity(), categories, 1);
//                        gridView.setAdapter(departmentAdapter);
//                    }else {
//                        Toast.makeText(getActivity(),"لا توجد اقسام حاليا", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    Toast.makeText(getActivity(),"صيغه استقبال غير صحيحه", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                if (error instanceof ServerError)
//                    Toast.makeText(getActivity(),"خطأ فى الاتصال بالخادم", Toast.LENGTH_SHORT).show();
//                else if (error instanceof TimeoutError)
//                    Toast.makeText(getActivity(),"خطأ فى مدة الاتصال", Toast.LENGTH_SHORT).show();
//                else if (error instanceof NetworkError)
//                    Toast.makeText(getActivity(),"شبكه الانترنت ضعيفه حاليا", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("catId",catId+"");
//                return map;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//                3,  // maxNumRetries = 2 means no retry
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }
//
//    /**
//     * http://abdelkreimahmed.000webhostapp.com/ListCat.php
//     * */
//    private void loadCategorys() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("جارى تحميل الاقسام ...");
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://abdelkreimahmed.000webhostapp.com/ListCat.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Response",response);
//                progressDialog.dismiss();
//                try {
//                    JSONObject object = new JSONObject(response);
//                    JSONArray array = object.getJSONArray("CatUser");
//                    if (array.length() > 0){
//                        for (int x=0; x<10; x++){
//                            JSONObject object1 = array.getJSONObject(x);
//                            Category category = new Category(object1.getInt("catId"),
//                                    object1.getInt("numUsers"),
//                                    object1.getString("catName"));
//                            categories.add(category);
//                        }
//                        departmentAdapter = new DepartmentAdapter(getActivity(), categories, 0);
//                        gridView.setAdapter(departmentAdapter);
//                    }else {
//                        Toast.makeText(getActivity(),"لا توجد اقسام حاليا", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    Toast.makeText(getActivity(),"صيغه استقبال غير صحيحه", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                if (error instanceof ServerError)
//                    Toast.makeText(getActivity(),"خطأ فى الاتصال بالخادم", Toast.LENGTH_SHORT).show();
//                else if (error instanceof TimeoutError)
//                    Toast.makeText(getActivity(),"خطأ فى مدة الاتصال", Toast.LENGTH_SHORT).show();
//                else if (error instanceof NetworkError)
//                    Toast.makeText(getActivity(),"شبكه الانترنت ضعيفه حاليا", Toast.LENGTH_SHORT).show();
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//                3,  // maxNumRetries = 2 means no retry
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }
}
