package com.example.a3dstep.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.a3dstep.Model.RankYear;
import com.example.a3dstep.R;

import java.util.List;

public class RankYearAdapter  extends ArrayAdapter<RankYear> {

	Activity context;
	int resource;
	List<RankYear> objects;
	/**
	 * @param context
	 * @param resource
	 * @param objects
	 * */
	public RankYearAdapter(Activity context, int resource, List<RankYear> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater= this.context.getLayoutInflater();
		View row = inflater.inflate(this.resource,null);

		LinearLayout background_ranking = (LinearLayout) row.findViewById(R.id.background_ranking);
		TextView txtNameRanking = (TextView) row.findViewById(R.id.txtNameRanking);
		TextView txtRanking = (TextView) row.findViewById(R.id.txtRanking);
		TextView txtStepRanking = (TextView) row.findViewById(R.id.txtStepRanking);
		ImageView imageAvataRanking = (ImageView) row.findViewById(R.id.imageAvataRanking);
		ImageView imgStepRanking =   (ImageView) row.findViewById(R.id.imgStepRanking);
		TextView txtRankingText = (TextView) row.findViewById(R.id.txtRankingText);
		/** Set data to row*/
		final RankYear rankYear = this.objects.get(position);
		txtStepRanking.setText(rankYear.getStepYear()+"");
		txtNameRanking.setText(rankYear.getNameRankYear()+"");
		txtRanking.setText(rankYear.getRankYear()+"");


		if(position == 0){
			background_ranking.setBackgroundResource(R.drawable.first_rank);
			txtStepRanking.setTextColor(Color.WHITE);
			txtNameRanking.setTextColor(Color.WHITE);
			txtRanking.setTextColor(Color.WHITE);
			txtRankingText.setTextColor(Color.WHITE);

		}
		else if(position == 1){
			background_ranking.setBackgroundResource(R.drawable.second_rank);
			txtStepRanking.setTextColor(Color.WHITE);
			txtNameRanking.setTextColor(Color.WHITE);
			txtRanking.setTextColor(Color.WHITE);
			txtRankingText.setTextColor(Color.WHITE);
		}
		else if(position == 2){
			background_ranking.setBackgroundResource(R.drawable.thirt_rank);
			txtStepRanking.setTextColor(Color.WHITE);
			txtNameRanking.setTextColor(Color.WHITE);
			txtRanking.setTextColor(Color.WHITE);
			txtRankingText.setTextColor(Color.WHITE);
		}
		else {
			background_ranking.setBackgroundResource(R.drawable.rank_list);
			txtStepRanking.setTextColor(Color.BLACK);
			txtNameRanking.setTextColor(Color.BLACK);
			txtRanking.setTextColor(Color.BLACK);
			txtRankingText.setTextColor(Color.BLACK);
		}

		/**Set Event Onclick*/

		return row;
	}

}