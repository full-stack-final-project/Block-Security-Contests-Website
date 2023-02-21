import random
from faker import Faker

fake = Faker()

def generate_wallet_address(length: int, count: int) -> set():
    seed = '1234567890ABCDEF'
    result = set()
    while len(result) != count:
        generated_string = ''.join((random.choice(seed)) for _ in range(length))
        result.add('0x' + generated_string)
    return result

def generate_password(length: int, count: int) -> set():
    seed = '1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+-*/!@#$%&'
    result = set()
    while len(result) != count:
        result.add(''.join((random.choice(seed)) for _ in range(length)))
    return result

def generate_company_name(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.company())
    return result

def generate_email(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.email())
    return result  

def generate_address(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.address())
    return result  

def generate_comment(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.text())
    return result 

def generate_title(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.catch_phrase())
    return result 

def generate_time(count: int) -> set():
    result = set()
    while len(result) != count:
        result.add(fake.iso8601(sep = ' '))
    return result

if __name__ == '__main__':
    wallets = list(generate_wallet_address(40, 60))
    passwords = list(generate_password(4, 60))
    company_names = list(generate_company_name(20))
    emails = list(generate_email(20))
    addresses = list(generate_address(20))
    comments = list(generate_comment(20))
    titles = list(generate_title(20))
    times = list(generate_time(50))

    with open('fake_data.txt', 'w') as file:
        file.write("Wallets: \n")
        for i in range(len(wallets)):
            file.write(wallets[i] + '\n')

        file.write("\n\nPasswords: \n")
        for i in range(len(passwords)):
            file.write(passwords[i] + '\n')

        file.write("\n\nCompany Names: \n")
        for i in range(len(company_names)):
            file.write(company_names[i] + '\n')

        file.write("\n\nEmails: \n")
        for i in range(len(emails)):
            file.write(emails[i] + '\n')

        file.write("\n\nAddress: \n")
        for i in range(len(addresses)):
            file.write(addresses[i] + '\n')

        file.write("\n\nComments: \n")
        for i in range(len(comments)):
            file.write(comments[i] + '\n')

        file.write("\n\nTitles: \n")
        for i in range(len(titles)):
            file.write(titles[i] + '\n')

        file.write("\n\nTimes: \n")
        for i in range(len(times)):
            file.write(times[i] + '\n')

    
            



