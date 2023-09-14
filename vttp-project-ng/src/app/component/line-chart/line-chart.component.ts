import { Component, inject } from '@angular/core';
import { ChartService } from 'src/app/service/chart.service';
import Chart from 'chart.js/auto';
import { Review } from 'src/app/models';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/service/session.service';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent {
  chart: any;
  chartInfo: any[] = [];
  labeldata: any[] = [];
  realdata: any[] = [];
  colordata: any[] = [];
  chartService = inject(ChartService);
  building!: string;
  route = inject(ActivatedRoute);
  router = inject(Router);
  sessionService = inject(SessionService);
  isLoading = false;
  noResults = false;

  ngOnInit(){
    this.isLoading = true;
    this.building = this.route.snapshot.params['building'];
    this.chartService.getChartInfo(this.building).subscribe((resp)=>{
      this.chartInfo = resp as Review[];
      if (this.chartInfo != null){
        
        this.chartInfo.sort((a ,b) => {
          return a.rentalStartDate - b.rentalStartDate;
        })
        
        for (let i = 0; i < this.chartInfo.length; i++){
          if(this.chartInfo[i].rentalStartDate != null){
            this.labeldata.push(new Date(this.chartInfo[i].rentalStartDate).toLocaleDateString());
            this.realdata.push(this.chartInfo[i].monthlyRentalCost)
          }
        }
      }
      this.isLoading = false;
      if(this.labeldata.length == 0 || this.realdata.length == 0) this.noResults = true;
      this.createChart(this.labeldata, this.realdata);
    })
  }

  createChart(labeldata: any, realdata: any){
    this.chart = new Chart('MyChart', {
      type: 'line',
      data: {
        labels: labeldata,
        datasets : [
          {
            label: 'Monthly rental cost($)',
            data: realdata
          }
        ]
      },
      options: {
        aspectRatio: 2
      }
    })
  }
  
  back(){
    this.router.navigateByUrl(this.sessionService.tempUrl);
  }
}
