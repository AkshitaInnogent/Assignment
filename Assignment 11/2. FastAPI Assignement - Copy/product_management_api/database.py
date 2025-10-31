from prisma import Prisma

# Initialize the Prisma client
prisma = Prisma()

# Functions to connect and disconnect the Prisma client
async def init_db():
    await prisma.connect()

async def close_db():
    await prisma.disconnect()
