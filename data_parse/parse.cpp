#include <iostream>

using namespace std;

int main()
{
    int a, b;
    while(cin >> a >> b && !(a == 0 && b == 0) )
    {
        cout << a << ",";
        int successCount = 0;
        int averageHops = 0;
        for(int i = 0; i < b; ++i)
        {
            int hops;
            bool success;
            cin >> hops 
                >> success;
            if(success) ++successCount;
            averageHops += hops;
        }
        cout << ((float)averageHops / (float)b) << "," 
             << ((float)successCount / (float)b) << endl;
    }
}
