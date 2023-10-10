# Stock-Market-Monitor
The project aims to monitor and analyze stocks listed on major US-based stock exchanges. Users can engage in simulated trading without risking real money and gather insights about the tickers they are interested in. The application provides a seamless and user-friendly experience through the use of Bootstrap and custom CSS.

My long-term goal is to leverage my previous experience as a financial analyst to conduct thorough validation on tickers.

The project was developed using Java/Spring for the backend, Angular for the frontend, and MySQL as the RDBMS. User authentication is implemented using JWT. Third-party APIs are integrated to access real-time and historical data on tickers. For instance, when a new ticker is listed, the application sends multiple requests to the Polygon API to retrieve historical data and download images and icons. Additionally, the FinnHub API is queried every 60 seconds to refresh the latest stock prices.
