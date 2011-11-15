#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <map>

using namespace std;

int main()
{
  map<int,vector<bool> > values;
  int currentRun = 0;
  string line;
  while(getline(cin,line) && !cin.eof())
  {
    if(line[0] == '=')
    { // we have a new series of data points
      currentRun = atoi(line.substr(1).c_str());
    }
    else
    {
      int temp = atoi(line.c_str());
      values[currentRun].push_back(temp <= 800);
    }
  }
  for(map<int,vector<bool> >::iterator values_itor = values.begin();
      values_itor != values.end(); ++values_itor)
  { // we are going to compute the percentage of winners
    // and format the output as a comma-seperated-file
    cout << values_itor->first << ",";
    int sum = 0;
    for(vector<bool>::iterator sum_itor = values_itor->second.begin(); 
        sum_itor != values_itor->second.end(); ++sum_itor)
    {
      if(*sum_itor) ++sum;
    }
    cout << ( static_cast<double>(sum) / 
              static_cast<double>(values_itor->second.size())  )
         << endl;
  }
}
